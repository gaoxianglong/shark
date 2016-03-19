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

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sharksharding.util.ResolveRWIndex;

/**
 * 在SQL执行之前进行数据路由
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
@Component
public class SQLExecute {
	private static Logger logger = LoggerFactory.getLogger(SQLExecute.class);

	@Resource
	private SharkJdbcTemplate jdbcTemplate;
	@Resource
	private SetDataSource setDataSource;
	@Resource(name = "dbRouteFacade")
	private Route route;

	/**
	 * 选择路由方式后,进行数据路由
	 * 
	 * @author gaoxianglong
	 * 
	 * @param proceedingJoinPoint
	 *            委托对象的上下文信息
	 * 
	 * @param indexType
	 *            true为master启始索引,false为slave启始索引
	 * 
	 * @exception Throwable
	 * 
	 * @return Object
	 */
	protected Object execute(ProceedingJoinPoint proceedingJoinPoint, boolean indexType) {
		long befor = System.currentTimeMillis();
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
				logger.info("sharding之前的SQL-->" + sql);
				if (jdbcTemplate.getIsShard()) {
					if (jdbcTemplate.getShardMode()) {
						params = route.dbRouteByOne(sql, params, indexType);
					} else {
						params = route.dbRouteByMany(sql, params, indexType);
					}
					sql = params[0].toString();
					logger.info("sharding之后的SQL-->" + sql);
				} else {
					/* 获取master/slave的数据源启始索引 */
					final int INDEX = ResolveRWIndex.getIndex(jdbcTemplate.getWr_index(), indexType);
					setDataSource.setIndex(INDEX);
				}
			}
			try {
				obj = proceedingJoinPoint.proceed(params);
				logger.debug("执行耗时->" + (System.currentTimeMillis() - befor) + "ms");
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}