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
 * 解析分库分表的关键字
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.0
 */
public class ResolveShardkey {
	/**
	 * 解析并获取配置文件中的分库分表关键字
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sharkInfo
	 *            分库分表配置信息
	 * 
	 * @return List<String> 分库分表关键字集合
	 */
	public static List<String> getKeys(SharkInfo sharkInfo) {
		// List<String> keyNames = null;
		// if (sharkInfo.getShardMode()) {
		// String dbKeyName = sharkInfo.getDbRuleArray().split("\\#")[1];
		// keyNames = Arrays.asList(dbKeyName.split("\\|"));
		// } else {
		// String dbKeyName = sharkInfo.getDbRuleArray().split("\\#")[1];
		// String tbKeyName = sharkInfo.getTbRuleArray().split("\\#")[1];
		// keyNames = Arrays.asList(dbKeyName.split("\\|"));
		// }
		String dbKeyName = sharkInfo.getDbRuleArray().split("\\#")[1];
		return Arrays.asList(dbKeyName.split("\\|"));
	}
}