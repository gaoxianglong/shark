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
package com.gxl.shark.util.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.gxl.shark.util.ResolveDbName;

/**
 * 生成基于druid的数据源配置文件
 * 
 * @author gaoxianglong
 */
public class CreateDruidXml implements CreateDSXml {
	private String isShard;
	private String name;
	private String username;
	private String password;
	private String url;
	private String initialSize;
	private String minIdle;
	private String maxActive;
	/* 是否缓存preparedStatement，也就是PSCache，在mysql下建议关闭，分库分表较多的数据库，建议配置为false */
	private String poolPreparedStatements;
	private String maxOpenPreparedStatements;
	/* 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能 */
	private String testOnBorrow;
	/* 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能 */
	private String testOnReturn;
	/* 建议配置为true，不影响性能 */
	private String testWhileIdle;
	private String filters;
	private String connectionProperties;
	/* 合并多个DruidDataSource的监控数据 */
	private String useGlobalDataSourceStat;
	/* 保存DruidDataSource的监控记录到日志中 */
	private String timeBetweenLogStatsMillis;
	private Marshaller marshaller;
	private String dbSize;
	private boolean isShow;
	private int dataSourceIndex;
	private boolean init_method;
	private String tbSuffix = "_0000";

	public CreateDruidXml() {
		try {
			JAXBContext jAXBContext = JAXBContext.newInstance(Beans.class);
			marshaller = jAXBContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean createDatasourceXml(File savePath) {
		boolean result = false;
		try {
			/* 创建配置文件的根目录<beans/>标签 */
			Beans beans = new Beans();
			List<Bean> beanList = new ArrayList<Bean>();
			for (int i = 0; i < Integer.parseInt(dbSize); i++) {
				/* 创建<bean/>子标签 */
				Bean bean = new Bean();
				bean.setId("dataSource" + (this.getDataSourceIndex() + i));
				bean.setClass_("com.alibaba.druid.pool.DruidDataSource");
				if (init_method)
					bean.setInit_method("init");
				bean.setDestroy_method("close");
				List<Property> propertys = new ArrayList<Property>();
				// Property name = new Property();
				// name.setName("name");
				// name.setValue(this.getName());
				Property username = new Property();
				username.setName("username");
				username.setValue(this.getUsername());
				Property password = new Property();
				password.setName("password");
				password.setValue(this.getPassword());
				Property url = new Property();
				url.setName("url");
				url.setValue(ResolveDbName.getNewDbName(i, this.getUrl(), tbSuffix));
				Property initialSize = new Property();
				initialSize.setName("initialSize");
				initialSize.setValue(this.getInitialSize());
				Property minIdle = new Property();
				minIdle.setName("minIdle");
				minIdle.setValue(this.getMinIdle());
				Property maxActive = new Property();
				maxActive.setName("maxActive");
				maxActive.setValue(this.getMaxActive());
				Property poolPreparedStatements = new Property();
				poolPreparedStatements.setName("poolPreparedStatements");
				poolPreparedStatements.setValue(this.getPoolPreparedStatements());
				Property maxOpenPreparedStatements = new Property();
				maxOpenPreparedStatements.setName("maxOpenPreparedStatements");
				maxOpenPreparedStatements.setValue(this.getMaxOpenPreparedStatements());
				Property testOnBorrow = new Property();
				testOnBorrow.setName("testOnBorrow");
				testOnBorrow.setValue(this.getTestOnBorrow());
				Property testOnReturn = new Property();
				testOnReturn.setName("testOnReturn");
				testOnReturn.setValue(this.getTestOnReturn());
				Property testWhileIdle = new Property();
				testWhileIdle.setName("testWhileIdle");
				testWhileIdle.setValue(this.getTestWhileIdle());
				Property filters = new Property();
				filters.setName("filters");
				filters.setValue(this.getFilters());
				Property connectionProperties = new Property();
				connectionProperties.setName("connectionProperties");
				connectionProperties.setValue(this.getConnectionProperties());
				Property useGlobalDataSourceStat = new Property();
				useGlobalDataSourceStat.setName("useGlobalDataSourceStat");
				useGlobalDataSourceStat.setValue(this.getUseGlobalDataSourceStat());
				Property timeBetweenLogStatsMillis = new Property();
				timeBetweenLogStatsMillis.setName("timeBetweenLogStatsMillis");
				timeBetweenLogStatsMillis.setValue(this.getTimeBetweenLogStatsMillis());
				// propertys.add(name);
				propertys.add(username);
				propertys.add(password);
				propertys.add(url);
				propertys.add(initialSize);
				propertys.add(minIdle);
				propertys.add(maxActive);
				propertys.add(poolPreparedStatements);
				propertys.add(maxOpenPreparedStatements);
				propertys.add(testOnBorrow);
				propertys.add(testOnReturn);
				propertys.add(testWhileIdle);
				propertys.add(filters);
				propertys.add(connectionProperties);
				propertys.add(useGlobalDataSourceStat);
				propertys.add(timeBetweenLogStatsMillis);
				bean.setProperty(propertys);
				beanList.add(bean);
			}
			beans.setBean(beanList);
			if (this.isShow())
				marshaller.marshal(beans, System.out);
			marshaller.marshal(beans, savePath);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getIsShard() {
		return isShard;
	}

	public void setIsShard(String isShard) {
		this.isShard = isShard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(String initialSize) {
		this.initialSize = initialSize;
	}

	public String getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(String minIdle) {
		this.minIdle = minIdle;
	}

	public String getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	public String getPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(String poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public String getMaxOpenPreparedStatements() {
		return maxOpenPreparedStatements;
	}

	public void setMaxOpenPreparedStatements(String maxOpenPreparedStatements) {
		this.maxOpenPreparedStatements = maxOpenPreparedStatements;
	}

	public String getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(String testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public String getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(String testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public String getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(String testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public String getUseGlobalDataSourceStat() {
		return useGlobalDataSourceStat;
	}

	public void setUseGlobalDataSourceStat(String useGlobalDataSourceStat) {
		this.useGlobalDataSourceStat = useGlobalDataSourceStat;
	}

	public String getTimeBetweenLogStatsMillis() {
		return timeBetweenLogStatsMillis;
	}

	public void setTimeBetweenLogStatsMillis(String timeBetweenLogStatsMillis) {
		this.timeBetweenLogStatsMillis = timeBetweenLogStatsMillis;
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public int getDataSourceIndex() {
		return dataSourceIndex;
	}

	public void setDataSourceIndex(int dataSourceIndex) {
		this.dataSourceIndex = dataSourceIndex;
	}

	public String getDbSize() {
		return dbSize;
	}

	public void setDbSize(String dbSize) {
		this.dbSize = dbSize;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}

	public boolean isInit_method() {
		return init_method;
	}

	public void setInit_method(boolean init_method) {
		this.init_method = init_method;
	}

	public String getTbSuffix() {
		return tbSuffix;
	}

	public void setTbSuffix(String tbSuffix) {
		this.tbSuffix = tbSuffix;
	}
}