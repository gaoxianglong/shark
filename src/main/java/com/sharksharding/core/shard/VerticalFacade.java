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

import java.util.List;

/**
 * 垂直分库,水平分片的一种关系型数据库分库分表模式
 *
 * @author gaoxianglong
 * 
 * @author 2.0.1
 */
public class VerticalFacade extends RouteImpl {
	private DataSourceHolder dataSourceHolder;

	public VerticalFacade() {
		dataSourceHolder = SharkDataSourceHolder.getDataSourceHolder();
	}

	@Override
	public Object[] route(String sql, Object[] params, boolean indexType) {
		String newSql = null;
		final String TB_RULE_ARRSY = shardConfigInfo.getTbRuleArray();
		List<String> keyNames = ResolveShardkey.getShardKeys(shardConfigInfo);
		if (keyNames.isEmpty())
			return null;
		/* 解析sql语句中的路由参数 */
		final long SHARD_VALUE = ResolveRouteValue.getRoute(sql, keyNames);
		Rule tbRule = getTbRule();
		final int TB_INDEX = tbRule.getIndex(SHARD_VALUE, TB_RULE_ARRSY);
		/* 解析数据库表名 */
		final String TB_NAME = ResolveTbName.getTbName(sql);
		/* 单库多表模式下设定真正的数据库表名 */
		newSql = SetTbName.setName(shardConfigInfo, TB_INDEX, TB_NAME, sql);
		final int DB_BEGIN_INDEX = ResolveIndex.getBeginIndex(shardConfigInfo.getWr_index(), indexType);
		/* 切换数据库的数据源索引 */
		SetDatasource.setIndex(DB_BEGIN_INDEX, dataSourceHolder);
		return updateParam(newSql, params);
	}
}