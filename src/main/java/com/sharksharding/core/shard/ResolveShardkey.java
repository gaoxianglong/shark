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

/**
 * 解析分库分表字段
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.0
 */
public class ResolveShardkey {
	/**
	 * 从分库分表算法中解shardkey
	 * 
	 * @author gaoxianglong
	 * 
	 * @param shardConfigInfo
	 *            分库分表配置信息
	 * 
	 * @return List<String> shardKeys
	 */
	public static List<String> getShardKeys(ShardConfigInfo shardConfigInfo) {
		final String dbRuleArray = shardConfigInfo.getDbRuleArray();
		final String tbRuleArray = shardConfigInfo.getTbRuleArray();
		String shardKey = null;
		if (null != dbRuleArray) {
			shardKey = dbRuleArray.split("\\#")[1];
			return Arrays.asList(shardKey.split("\\|"));
		} else if (null != tbRuleArray) {
			shardKey = tbRuleArray.split("\\#")[1];
			return Arrays.asList(shardKey.split("\\|"));
		}
		return null;
	}
}