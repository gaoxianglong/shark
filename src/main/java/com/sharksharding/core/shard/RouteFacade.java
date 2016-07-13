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

import com.sharksharding.factory.DataSourceHolderFactory;
import com.sharksharding.factory.DbRuleFactory;
import com.sharksharding.factory.RuleFactory;
import com.sharksharding.factory.TbRuleFactory;

/**
 * 分库分表模式的外观类
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class RouteFacade implements Route {
	private ShardConfigInfo shardConfigInfo;

	public RouteFacade() {
		shardConfigInfo = ShardConfigInfo.getShardInfo();
	}

	@Override
	public Object[] routeSingle(String sql, Object[] params, boolean indexType) {
		String newSql = null;
		String tbRuleArray = shardConfigInfo.getTbRuleArray();
		List<String> keyNames = ResolveShardkey.getShardKeys(shardConfigInfo);
		if (keyNames.isEmpty())
			return null;
		/* 解析sql语句中的路由参数 */
		long shardValue = ResolveRouteValue.getRoute(sql, keyNames);
		Rule tbRule = getTbRule();
		int tbIndex = tbRule.getIndex(shardValue, tbRuleArray);
		/* 解析数据库表名 */
		final String tbName = ResolveTbName.getTbName(sql);
		/* 单库多表模式下设定真正的数据库表名 */
		newSql = SetTbName.setName(shardConfigInfo, tbIndex, tbName, sql);
		final int index = ResolveIndex.getIndex(shardConfigInfo.getWr_index(), indexType);
		/* 切换数据源索引 */
		SetDatasource.setIndex(index, DataSourceHolderFactory.getDataSourceHolder());
		return updateParam(newSql, params);
	}

	@Override
	public Object[] routeMany(String sql, Object[] params, boolean indexType) {
		String newSql = null;
		String dbRuleArray = shardConfigInfo.getDbRuleArray();
		String tbRuleArray = shardConfigInfo.getTbRuleArray();
		List<String> keyNames = ResolveShardkey.getShardKeys(shardConfigInfo);
		if (keyNames.isEmpty())
			return null;
		/* 解析sql语句中的路由条件 */
		long shardValue = ResolveRouteValue.getRoute(sql, keyNames);
		Rule dbRule = getDbRule();
		int dbIndex = dbRule.getIndex(shardValue, dbRuleArray);
		Rule tbRule = getTbRule();
		int tbIndex = tbRule.getIndex(shardValue, tbRuleArray);
		/* 解析配置文件中数据库和数据库表的数量 */
		String values[] = tbRuleArray.split("[\\%]");
		int tbSize = Integer.parseInt(values[1]);
		int dbSize = Integer.parseInt(values[2]);
		/* 解析数据库表名 */
		final String tbName = ResolveTbName.getTbName(sql);
		/* 单库多表模式下设定真正的数据库表名 */
		newSql = SetTbName.setName(shardConfigInfo, dbIndex, tbIndex, dbSize, tbSize, tbName, sql);
		int index = ResolveIndex.getIndex(shardConfigInfo.getWr_index(), indexType);
		dbIndex += index;
		/* 切换数据源索引 */
		SetDatasource.setIndex(dbIndex, DataSourceHolderFactory.getDataSourceHolder());
		return updateParam(newSql, params);
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

	public Rule getDbRule() {
		RuleFactory dbRuleFactory = new DbRuleFactory();
		return dbRuleFactory.getRule();
	}

	public Rule getTbRule() {
		RuleFactory tbRuleFactory = new TbRuleFactory();
		return tbRuleFactory.getRule();
	}
}