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
package com.gxl.shark.core.shard;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.gxl.shark.core.shard.SharkJdbcTemplate;

/**
 * 解析分库规则后计算数据源索引
 * 
 * @author gaoxianglong
 */
@Component
public class DbRule extends RuleImpl {
	@Resource
	private SharkJdbcTemplate jdbcTemplate;

	@Override
	public int getIndex(long routeValue, String ruleArray) {
		int dbIndex = -1;
		if (jdbcTemplate.getShardMode()) {
			dbIndex = getDbIndexbyOne(routeValue, ruleArray);
		} else {
			dbIndex = getDbIndex(routeValue, ruleArray);
		}
		return dbIndex;
	}
}