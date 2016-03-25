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
package com.sharksharding.resources.conn;

import com.sharksharding.exception.ValidateException;
import com.sharksharding.resources.register.bean.RegisterDataSource;
import com.sharksharding.resources.watcher.RedisWatcher;
import com.sharksharding.util.MD5Util;
import redis.clients.jedis.JedisCluster;

/**
 * 客户端连接管理器,处理与rediscluster服务器的session会话
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class RedisConnectionManager {
	private RedisWatcher redisWatcher;

	public RedisWatcher getRedisWatcher() {
		return redisWatcher;
	}

	public void setRedisWatcher(RedisWatcher redisWatcher) {
		this.redisWatcher = redisWatcher;
	}

	private String key;
	/* 0使用版本号比对,1使用MD5校验 */
	private static int type = 0;
	private JedisCluster jedisCluster;

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	private RedisConnectionManager(String key, JedisCluster jedisCluster) {
		this(key, jedisCluster, type);
	}

	private RedisConnectionManager(String key, JedisCluster jedisCluster, int type) {
		this.key = key;
		this.jedisCluster = jedisCluster;
		if (!(type >= 0 && type <= 1)) {
			throw new ValidateException("type configuration error");
		} else {
			this.type = type;
		}
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
	 * 
	 * @return void
	 */
	public void getResource() {
		String value = null;
		switch (type) {
		case 0:
			value = jedisCluster.get(key).split("(%@%)")[1];
			break;
		case 1:
			value = jedisCluster.get(key);
			RedisWatcher.setMd5Code(MD5Util.toMd5Code(value));
			break;
		default:
			break;
		}
		if (null != value) {
			redisWatcher.init(jedisCluster, key, type);
			/* 向ioc容器中动态注册相关bean实例 */
			RegisterDataSource.register(value, "redis");
		}
	}
}