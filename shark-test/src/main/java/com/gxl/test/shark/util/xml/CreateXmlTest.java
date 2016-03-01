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
package com.gxl.test.shark.util.xml;

import java.io.File;
import org.junit.Test;
import com.gxl.shark.util.xml.CreateC3p0Xml;
import com.gxl.shark.util.xml.CreateCoreXml;
import com.gxl.shark.util.xml.CreateDruidXml;
import junit.framework.Assert;

/**
 * 自动生成配置文件测试类
 * 
 * @author gaoxianglong
 */
public class CreateXmlTest {
	/**
	 * 生成核心配置文件
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testCreateCoreXml() {
		CreateCoreXml c_xml = new CreateCoreXml();
		/* 是否控制台输出生成的配置文件 */
		c_xml.setIsShow(true);
		/* 配置分库分片信息 */
		c_xml.setDbSize("64");
		c_xml.setShard("true");
		c_xml.setWr_index("r0w32");
		c_xml.setShardMode("false");
		c_xml.setConsistent("false");
		c_xml.setDbRuleArray("#userinfo_id|email_hash# % 1024 / 32");
		c_xml.setTbRuleArray("#userinfo_id|email_hash# % 1024 % 32");
		c_xml.setSqlPath("classpath:properties/sqlFile.properties");
		/* 执行配置文件输出 */
		Assert.assertTrue(c_xml.createCoreXml(new File("e:/shark-context.xml")));
	}

	/**
	 * 生成c3p0数据源配置文件
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testCreateC3p0Xml() {
		CreateC3p0Xml c_xml = new CreateC3p0Xml();
		/* 是否控制台输出生成的配置文件 */
		c_xml.setIsShow(true);
		c_xml.setTbSuffix("_0000");
		/* 数据源索引起始 */
		c_xml.setDataSourceIndex(1);
		/* 配置分库分片信息 */
		c_xml.setDbSize("16");
		/* 配置数据源信息 */
		c_xml.setJdbcUrl("jdbc:mysql://ip1:3306/db");
		c_xml.setUser("${name}");
		c_xml.setPassword("${password}");
		c_xml.setDriverClass("${driverClass}");
		c_xml.setInitialPoolSize("${initialPoolSize}");
		c_xml.setMinPoolSize("${minPoolSize}");
		c_xml.setMaxPoolSize("${maxPoolSize}");
		c_xml.setMaxStatements("${maxStatements}");
		c_xml.setMaxIdleTime("${maxIdleTime}");
		/* 执行配置文件输出 */
		Assert.assertTrue(c_xml.createDatasourceXml(new File("e:/dataSource-context.xml")));
	}

	/**
	 * 生成c3p0的master/slave数据源配置文件
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testCreateC3p0MSXml() {
		CreateC3p0Xml c_xml = new CreateC3p0Xml();
		c_xml.setIsShow(true);
		c_xml.setTbSuffix("_0000");
		/* 生成master数据源信息 */
		c_xml.setDataSourceIndex(1);
		c_xml.setDbSize("16");
		c_xml.setJdbcUrl("jdbc:mysql://ip1:3306/db");
		c_xml.setUser("${name}");
		c_xml.setPassword("${password}");
		c_xml.setDriverClass("${driverClass}");
		c_xml.setInitialPoolSize("${initialPoolSize}");
		c_xml.setMinPoolSize("${minPoolSize}");
		c_xml.setMaxPoolSize("${maxPoolSize}");
		c_xml.setMaxStatements("${maxStatements}");
		c_xml.setMaxIdleTime("${maxIdleTime}");
		Assert.assertTrue(c_xml.createDatasourceXml(new File("e:/masterDataSource-context.xml")));
		/* 生成slave数据源信息 */
		c_xml.setDataSourceIndex(17);
		c_xml.setDbSize("16");
		c_xml.setJdbcUrl("jdbc:mysql://ip2:3306/db");
		c_xml.setUser("${name}");
		c_xml.setPassword("${password}");
		Assert.assertTrue(c_xml.createDatasourceXml(new File("e:/slaveDataSource-context.xml")));
	}

	/**
	 * 生成druid数据源配置文件
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testCreateDruidXml() {
		CreateDruidXml c_xml = new CreateDruidXml();
		/* 是否控制台输出生成的配置文件 */
		c_xml.setIsShow(true);
		/* 数据源索引起始 */
		c_xml.setDataSourceIndex(1);
		/* 配置分库分片信息 */
		c_xml.setDbSize("16");
		/* false为懒加载模式，反之启动时开始初始化数据源 */
		c_xml.setInit_method(true);
		c_xml.setTbSuffix("_0000");
		/* 生成数据源信息 */
		c_xml.setUsername("${username}");
		c_xml.setPassword("${password}");
		c_xml.setUrl("jdbc:mysql://ip1:3306/db");
		c_xml.setInitialSize("${initialSize}");
		c_xml.setMinIdle("${minIdle}");
		c_xml.setMaxActive("${maxActive}");
		c_xml.setPoolPreparedStatements("${poolPreparedStatements}");
		c_xml.setMaxOpenPreparedStatements("${maxOpenPreparedStatements}");
		c_xml.setTestOnBorrow("${testOnBorrow}");
		c_xml.setTestOnReturn("${testOnReturn}");
		c_xml.setTestWhileIdle("${testWhileIdle}");
		c_xml.setFilters("${filters}");
		c_xml.setConnectionProperties("${connectionProperties}");
		c_xml.setUseGlobalDataSourceStat("${useGlobalDataSourceStat}");
		c_xml.setTimeBetweenLogStatsMillis("${timeBetweenLogStatsMillis}");
		/* 执行配置文件输出 */
		Assert.assertTrue(c_xml.createDatasourceXml(new File("e:/dataSource-context.xml")));
	}

	/**
	 * 生成druid数据源配置文件
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testCreateDruidMSXml() {
		CreateDruidXml c_xml = new CreateDruidXml();
		/* 是否控制台输出生成的配置文件 */
		c_xml.setIsShow(true);
		/* 数据源索引起始 */
		c_xml.setDataSourceIndex(1);
		/* 配置分库分片信息 */
		c_xml.setDbSize("16");
		/* false为懒加载模式，反之启动时开始初始化数据源 */
		c_xml.setInit_method(true);
		c_xml.setTbSuffix("_0000");
		/* 生成master数据源信息 */
		c_xml.setUsername("${username}");
		c_xml.setPassword("${password}");
		c_xml.setUrl("jdbc:mysql://ip1:3306/db");
		c_xml.setInitialSize("${initialSize}");
		c_xml.setMinIdle("${minIdle}");
		c_xml.setMaxActive("${maxActive}");
		c_xml.setPoolPreparedStatements("${poolPreparedStatements}");
		c_xml.setMaxOpenPreparedStatements("${maxOpenPreparedStatements}");
		c_xml.setTestOnBorrow("${testOnBorrow}");
		c_xml.setTestOnReturn("${testOnReturn}");
		c_xml.setTestWhileIdle("${testWhileIdle}");
		c_xml.setFilters("${filters}");
		c_xml.setConnectionProperties("${connectionProperties}");
		c_xml.setUseGlobalDataSourceStat("${useGlobalDataSourceStat}");
		c_xml.setTimeBetweenLogStatsMillis("${timeBetweenLogStatsMillis}");
		/* 执行配置文件输出 */
		Assert.assertTrue(c_xml.createDatasourceXml(new File("e:/masterDataSource-context.xml")));
		/* 生成slave数据源信息 */
		c_xml.setDataSourceIndex(17);
		c_xml.setDbSize("16");
		c_xml.setUsername("${username}");
		c_xml.setPassword("${password}");
		c_xml.setUrl("jdbc:mysql://ip2:3306/db");
		/* 执行配置文件输出 */
		Assert.assertTrue(c_xml.createDatasourceXml(new File("e:/slaveDataSource-context.xml")));
	}
}