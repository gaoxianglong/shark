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

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sharksharding.util.GetKeyName;
import com.sharksharding.util.ResolveRWIndex;
import com.sharksharding.util.ResolveRoute;
import com.sharksharding.util.ResolveTableName;

/**
 * 路由模式的外观类
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
@Component
public class DbRouteFacade implements Route {
	@Resource
	private SharkJdbcTemplate jdbcTemplate;
	@Resource
	private SetTabName setTabName;
	@Resource
	private DbRule dbRule;
	@Resource
	private TabRule tabRule;
	@Resource
	private SetDataSource setDataSource;
	
	@Override
	public Object[] dbRouteByOne(String sql, Object[] params, boolean indexType) {
		String newSQL = null;
		String dbRuleArray = jdbcTemplate.getDbRuleArray();
		List<String> keyNames = GetKeyName.getName(true, dbRuleArray, null);
		if (keyNames.isEmpty())
			return null;
		/* 解析SQL语句中的路由条件 */
		long routeValue = ResolveRoute.getRoute(sql, keyNames);
		int dbIndex = dbRule.getIndex(routeValue, dbRuleArray);
		if (jdbcTemplate.getConsistent()) {
			/* 解析数据库表名 */
			final String TAB_NAME = ResolveTableName.getTabName(sql);
			/* 设置片名 */
			newSQL = setTabName.setName(dbIndex, TAB_NAME, sql);
		} else {
			newSQL = sql;
		}
		int index = ResolveRWIndex.getIndex(jdbcTemplate.getWr_index(), indexType);
		dbIndex += index;
		setDataSource.setIndex(dbIndex);
		return updateParam(newSQL, params);
	}

	@Override
	public Object[] dbRouteByMany(String sql, Object[] params, boolean indexType) {
		String newSQL = null;
		String dbRuleArray = jdbcTemplate.getDbRuleArray();
		String tbRuleArray = jdbcTemplate.getTbRuleArray();
		List<String> keyNames = GetKeyName.getName(false, dbRuleArray, tbRuleArray);
		if (keyNames.isEmpty())
			return null;
		/* 解析SQL语句中的路由条件 */
		long routeValue = ResolveRoute.getRoute(sql, keyNames);
		int dbIndex = dbRule.getIndex(routeValue, dbRuleArray);
		int tbIndex = tabRule.getIndex(routeValue, tbRuleArray);
		/* 解析配置文件中数据库和数据库表的数量 */
		String values[] = tbRuleArray.split("[\\%]");
		int tbSize = Integer.parseInt(values[1]);
		int dbSize = Integer.parseInt(values[2]);
		/* 解析数据库表名 */
		final String TAB_NAME = ResolveTableName.getTabName(sql);
		/* 设置片名 */
		newSQL = setTabName.setName(dbIndex, tbIndex, dbSize, tbSize, TAB_NAME, sql);
		int index = ResolveRWIndex.getIndex(jdbcTemplate.getWr_index(), indexType);
		dbIndex += index;
		setDataSource.setIndex(dbIndex);
		return updateParam(newSQL, params);
	}

	/**
	 * 重写委托对象的上下文信息，替换原先入参
	 *
	 * @author gaoxianglong
	 * 
	 * @param newSQL
	 *            持有真正片名的sql
	 * 
	 * @param params
	 *            委托对象的方法入参
	 * 
	 * @return Object[] 替换后的方法入参
	 */
	private Object[] updateParam(String newSQL, Object[] params) {
		List<Object> newParams = Arrays.asList(params);
		newParams.set(0, newSQL);
		return newParams.toArray();
	}
}