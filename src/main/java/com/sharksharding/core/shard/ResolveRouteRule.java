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

import java.util.ArrayList;
import java.util.List;

/**
 * 解析路由规则
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class ResolveRouteRule {
	public static List<Integer> resolveDbRule(String dbRuleArray) {
		List<Integer> list = new ArrayList<Integer>();
		final int TB_SIZE = Integer
				.parseInt(dbRuleArray.substring(dbRuleArray.indexOf("%") + 1, dbRuleArray.indexOf("/")));
		final int DB_SIZE = Integer.parseInt(dbRuleArray.substring(dbRuleArray.indexOf("/") + 1));
		list.add(TB_SIZE);
		list.add(DB_SIZE);
		return list;
	}

	public static List<Integer> resolveTbRule(String tbRuleArray, boolean shardMode) {
		List<Integer> list = new ArrayList<Integer>();
		String values[] = tbRuleArray.split("[\\%]");
		if (shardMode) {
			final int TB_SIZE = Integer.parseInt(values[1]);
			final int DB_SIZE = Integer.parseInt(values[2]);
			list.add(TB_SIZE);
			list.add(DB_SIZE);
		} else {
			final int TB_SIZE = Integer.parseInt(values[1]);
			list.add(TB_SIZE);
		}
		return list;
	}
}