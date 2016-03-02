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
package com.gxl.shark.resources.watcher;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.gxl.shark.resources.register.bean.RegisterBean;
import redis.clients.jedis.JedisCluster;

/**
 * sharding、数据源相关的redis节点watcher
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
@Component
public class RedisWatcher {
	@Resource
	private RegisterBean registerBean;
	private JedisCluster jedisCluster;
	private String key;
	private static int version = 1;
	private Logger logger = LoggerFactory.getLogger(RedisWatcher.class);

	public void init(JedisCluster jedisCluster, String key) {
		this.jedisCluster = jedisCluster;
		this.key = key;
	}

	@Scheduled(cron = "0/5 * * * * ?")
	private void run() {
		if (null != jedisCluster && null != key) {
			logger.debug("redisWatch执行中...");
			final String[] values = jedisCluster.get(key).split("\\,");
			final int version = Integer.valueOf(values[0]);
			final String resource = values[1];
			/* 如果版本发生变化，则重新向ioc容器中注册相关bean实例 */
			if (RedisWatcher.version != version) {
				registerBean.register(resource);
				RedisWatcher.version = version;
				logger.debug("resource version-->" + version);
			}
		}
	}
}