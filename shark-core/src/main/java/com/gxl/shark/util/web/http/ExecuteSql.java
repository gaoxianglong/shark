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
package com.gxl.shark.util.web.http;

import java.util.Map;

import com.gxl.shark.core.shard.SharkJdbcTemplate;

/**
 * 执行sql操作
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class ExecuteSql {
	private SharkJdbcTemplate jdbcTemplate;

	protected ExecuteSql(SharkJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 执行数据检索
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sql
	 *            sql信息
	 * 
	 * @param Exception
	 * 
	 * @return Map<String, Object> 结果集
	 */
	public Map<String, Object> queryData(String sql) throws Exception {
		return jdbcTemplate.queryForMap(sql);
	}

	// /**
	// * 执行数据更新、插入、删除
	// *
	// * @author gaoxianglong
	// *
	// * @param sql
	// * sql信息
	// *
	// * @param Exception
	// *
	// * @return void
	// */
	// public void updateData(String sql) throws Exception {
	// jdbcTemplate.update(sql);
	// }
}