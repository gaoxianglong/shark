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
package com.gxl.shark.resources.conn;

import javax.annotation.Resource;
import com.gxl.shark.resources.register.bean.RegisterBean;
import com.gxl.shark.resources.watcher.RedisWatcher;
import redis.clients.jedis.JedisCluster;

/**
 * 客户端连接管理器,处理与rediscluster服务器的session会话
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class RedisConnectionManager {
	@Resource(name = "registerDataSource")
	private RegisterBean registerBean;
	
	@Resource
	private RedisWatcher redisWatcher;
	private String key;
	private JedisCluster jedisCluster;

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	private RedisConnectionManager(String key, JedisCluster jedisCluster) {
		this.key = key;
		this.jedisCluster = jedisCluster;
	}

	/**
	 * 初始化方法
	 *
	 * @author gaoxianglong
	 */
	@SuppressWarnings("unused")
	private void init() {
		getResource();
	}

	/**
	 * 从配置中心拉取出配置信息
	 * 
	 * @author gaoxianglong
	 */
	public void getResource() {
		final String[] values = jedisCluster.get(key).split("\\,");
		final String value = values[1];
		if (null != value) {
			redisWatcher.init(jedisCluster, key);
			/* 向ioc容器中动态注册相关bean实例 */
			registerBean.register(value);
		}
	}
}