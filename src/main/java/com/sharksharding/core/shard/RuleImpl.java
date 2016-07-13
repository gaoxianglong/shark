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
 * 路由规则接口实现
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public abstract class RuleImpl implements Rule {
	@Override
	public int getIndex(long route, String ruleArray) {
		return -1;
	}

	/**
	 * 根据分库规则计算出数据源索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param shardKey
	 *            路由条件
	 * 
	 * @param dbRuleArray
	 *            分库规则
	 * 
	 * @return int 数据源索引
	 */
	public static int getDbIndex(long shardKey, String dbRuleArray) {
		int index = -1;
		List<Integer> list = ResolveRouteRule.resolveDbRule(dbRuleArray);
		if (!list.isEmpty()) {
			final int tbSize = list.get(0);
			final int dbSize = list.get(1);
			/* shardKey % tbSize / dbSize */
			index = (int) ((shardKey & (tbSize - 1)) / dbSize);
		}
		return index;
	}

	/**
	 * 根据分片规则计算出数据库表索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param shardKey
	 *            路由条件
	 * 
	 * @param tbRuleArray
	 *            分表规则
	 * 
	 * @param shardMode
	 *            分库分表模式
	 * 
	 * @return int 数据库表索引
	 */
	public static int getTbIndex(long shardKey, String tbRuleArray, boolean shardMode) {
		int index = -1;
		List<Integer> list = ResolveRouteRule.resolveTbRule(tbRuleArray, shardMode);
		if (!list.isEmpty()) {
			if (shardMode) {
				final int tbSize = list.get(0);
				final int dbSize = list.get(1);
				/* shardKey % tbSize % dbSize */
				return (int) (shardKey & (tbSize - 1) & (dbSize - 1));
			} else {
				final int tbSize = list.get(0);
				/* shardKey % tbSize */
				return (int) (shardKey & (tbSize - 1));
			}
		}
		return index;
	}
}