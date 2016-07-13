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

import org.springframework.jdbc.core.JdbcTemplate;

import com.sharksharding.resources.register.bean.RegisterDataSource;

/**
 * 从zookeeper、redis等配置中心读取到相关配置后,
 * 从此类中获取JdbcTemplate实例,避免watcher监听到事件后重新注册相关bean时,应用持有的还是之前引用
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class GetJdbcTemplate {
	private static JdbcTemplate jdbcTemplate;

	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 通过指定的beanName从ioc容器中获取JdbcTemplate实例
	 * 
	 * @author gaoxianglong
	 * 
	 * @param beanName
	 * 
	 * @return JdbcTemplate
	 */
	public static JdbcTemplate getJdbcTemplate(String beanName) {
		return RegisterDataSource.getBean(beanName);
	}

	public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		GetJdbcTemplate.jdbcTemplate = jdbcTemplate;
	}
}