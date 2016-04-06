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
package com.test.sharksharding.core.shard;

import java.lang.reflect.Constructor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sharksharding.sql.PropertyPlaceholderConfigurer;

/**
 * PropertyPlaceholderConfigurer测试用例
 * 
 * @author gaoxianglong
 */
public class PropertyPlaceholderConfigurerTest {
	private static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer;
	private static Logger logger = LoggerFactory.getLogger(PropertyPlaceholderConfigurerTest.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public @BeforeClass static void init() {
		try {
			Class<PropertyPlaceholderConfigurer> clss = (Class<PropertyPlaceholderConfigurer>) Class
					.forName("com.sharksharding.sql.PropertyPlaceholderConfigurer");
			Constructor[] constructors = clss.getDeclaredConstructors();
			Constructor constructor = constructors[0];
			constructor.setAccessible(true);
			propertyPlaceholderConfigurer = (PropertyPlaceholderConfigurer) constructor
					.newInstance(new Object[] { "classpath:properties/sql.properties" });
			logger.info("new instance success");
		} catch (Exception e) {
			logger.error("new instance fail", e);
		}
	}

	public @Test void testLoad() {
		String sql = propertyPlaceholderConfigurer.getSql("update", 123456);
		logger.info("sql-->" + sql);
		sql = propertyPlaceholderConfigurer.getSql("insert", 123456);
		logger.info("sql-->" + sql);
		sql = propertyPlaceholderConfigurer.getSql("select", 123456);
		logger.info("sql-->" + sql);
		sql = propertyPlaceholderConfigurer.getSql("delete", 123456);
		logger.info("sql-->" + sql);
	}
}