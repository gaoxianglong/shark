/*
 * Copyright 2015-2101 gaoxianglong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sharksharding.core.shard;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sharksharding.factory.HorizontalFacadeFactory;
import com.sharksharding.factory.RouteFactory;
import com.sharksharding.factory.VerticalFacadeFactory;

/**
 * 在sql执行之前进行数据路由
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class SQLExecute {
	private ShardConfigInfo sharkInfo;
	private DataSourceHolder dataSourceHolder;
	private Route horizontalFacade, verticalFacade;
	private Logger logger = LoggerFactory.getLogger(SQLExecute.class);

	public SQLExecute() {
		sharkInfo = ShardConfigInfo.getShardInfo();
		dataSourceHolder = SharkDataSourceHolder.getDataSourceHolder();
		RouteFactory horizontalFacadeFactory = new HorizontalFacadeFactory();
		RouteFactory verticalFacadeFactory = new VerticalFacadeFactory();
		horizontalFacade = horizontalFacadeFactory.getRoute();
		verticalFacade = verticalFacadeFactory.getRoute();
	}

	/**
	 * 选择路由方式后,进行数据路由
	 * 
	 * @author gaoxianglong
	 * 
	 * @param proceedingJoinPoint
	 *            委托对象(即:目标对象/被代理的对象)的上下文信息
	 * 
	 * @param indexType
	 *            true为master启始索引,false为slave启始索引,读写分离
	 * 
	 * @exception Throwable
	 * 
	 * @return Object
	 */
	protected Object execute(ProceedingJoinPoint proceedingJoinPoint, boolean indexType) {
		Object obj = null;
		if (null != proceedingJoinPoint) {
			Object[] params = proceedingJoinPoint.getArgs();
			if (0 > params.length)
				return obj;
			Object param = params[0];
			/*
			 * org.springframework.jdbc.core.JdbcTemplate的update*()和query*()
			 * 方法的第一参数都是SQL
			 */
			if (param instanceof String) {
				String sql = param.toString();
				logger.debug("before sql-->" + sql);
				/* 检查分库分表开关是否打开 */
				if (sharkInfo.getIsShard()) {
					if (sharkInfo.getShardMode()) {
						params = horizontalFacade.route(sql, params, indexType);
					} else {
						params = verticalFacade.route(sql, params, indexType);
					}
					sql = params[0].toString();
				} else {
					/* 获取master/slave的数据源启始索引 */
					final int index = ResolveIndex.getBeginIndex(sharkInfo.getWr_index(), indexType);
					SetDatasource.setIndex(index, dataSourceHolder);
				}
				logger.debug("after sql-->" + sql);
			}
			try {
				obj = proceedingJoinPoint.proceed(params);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}