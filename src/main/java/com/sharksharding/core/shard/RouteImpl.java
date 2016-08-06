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
public abstract class RouteImpl implements Route {
	protected ShardConfigInfo shardConfigInfo;

	protected RouteImpl() {
		shardConfigInfo = ShardConfigInfo.getShardInfo();
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
	protected Object[] updateParam(String newSQL, Object[] params) {
		List<Object> newParams = Arrays.asList(params);
		newParams.set(0, newSQL);
		return newParams.toArray();
	}

	protected Rule getDbRule() {
		RuleFactory dbRuleFactory = new DbRuleFactory();
		return dbRuleFactory.getRule();
	}

	protected Rule getTbRule() {
		RuleFactory tbRuleFactory = new TbRuleFactory();
		return tbRuleFactory.getRule();
	}
}