package com.test.sharksharding.use.resource;
///*
// * Copyright 2015-2101 gaoxianglong
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.test.sharksharding.resource;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.HashSet;
//import java.util.Set;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
///**
// * 向zookeeper配置中心添加数据
// * 
// * @author gaoxianglong
// * 
// * @version 1.3.7
// */
//public class redisSetData {
//	private static JedisCluster jedis;
//	private Logger logger = LoggerFactory.getLogger(redisSetData.class);
//
//	public @BeforeClass static void init() {
//		GenericObjectPoolConfig cfg = new GenericObjectPoolConfig();
//		cfg.setMaxIdle(10);
//		cfg.setMinIdle(1);
//		cfg.setMaxIdle(5);
//		cfg.setMaxWaitMillis(5000);
//		cfg.setTestOnBorrow(true);
//		cfg.setTestOnReturn(true);
//		Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
//		HostAndPort hostAndPort = new HostAndPort("120.24.75.22", 7000);
//		hostAndPorts.add(hostAndPort);
//		jedis = new JedisCluster(hostAndPorts, cfg);
//	}
//
//	public @Test void testVersionSetData() {
//		try (BufferedReader reader = new BufferedReader(new FileReader("c:/shark-datasource.xml"))) {
//			StringBuffer str = new StringBuffer();
//			String value = "";
//			while (null != (value = reader.readLine()))
//				str.append(value);
//			jedis.set("shark-datasource", "2%@%" + str.toString());
//			logger.info("insert success");
//		} catch (Exception e) {
//			logger.error("insert fail", e);
//		}
//	}
//
//	public @Test void testMd5SetData() {
//		try (BufferedReader reader = new BufferedReader(new FileReader("c:/shark-datasource.xml"))) {
//			StringBuffer str = new StringBuffer();
//			String value = "";
//			while (null != (value = reader.readLine()))
//				str.append(value);
//			jedis.set("shark-datasource", str.toString());
//			logger.info("insert success");
//		} catch (Exception e) {
//			logger.error("insert fail", e);
//		}
//	}
//}