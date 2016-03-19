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

/**
 * 从zookeeper配置中心读取到相关配置后,
 * 从此类中获取SharkJdbcTemplate实例,避免watcher监听到事件后重新注册相关bean时,应用持有的还是之前引用
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class GetJdbcTemplate {
	private static SharkJdbcTemplate jdbcTemplate;

	public static SharkJdbcTemplate getSharkJdbcTemplate() {
		return jdbcTemplate;
	}

	public static void setSharkJdbcTemplate(SharkJdbcTemplate jdbcTemplate) {
		GetJdbcTemplate.jdbcTemplate = jdbcTemplate;
	}
}