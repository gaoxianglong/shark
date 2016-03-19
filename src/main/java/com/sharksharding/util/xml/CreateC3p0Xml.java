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
package com.sharksharding.util.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sharksharding.util.ResolveDbName;

/**
 * 生成基于c3p0的数据源配置文件
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class CreateC3p0Xml implements CreateDSXml {
	private String isShard;
	private String dbSize;
	private String user;
	private String password;
	private String jdbcUrl;
	private String driverClass;
	private String initialPoolSize;
	private String minPoolSize;
	private String maxPoolSize;
	private String maxStatements;
	private String maxIdleTime;
	private String consistent;
	private Marshaller marshaller;
	private boolean isShow;
	private int dataSourceIndex;
	private String tbSuffix = "_0000";

	public CreateC3p0Xml() {
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
				bean.setClass_("com.mchange.v2.c3p0.ComboPooledDataSource");
				bean.setDestroy_method("close");
				List<Property> propertys = new ArrayList<Property>();
				Property user = new Property();
				user.setName("user");
				user.setValue(this.getUser());
				Property password = new Property();
				password.setName("password");
				password.setValue(this.getPassword());
				Property jdbcUrl = new Property();
				jdbcUrl.setName("jdbcUrl");
				jdbcUrl.setValue(ResolveDbName.getNewDbName(i, this.getJdbcUrl(), tbSuffix));
				Property driverClass = new Property();
				driverClass.setName("driverClass");
				driverClass.setValue(this.getDriverClass());
				Property initialPoolSize = new Property();
				initialPoolSize.setName("initialPoolSize");
				initialPoolSize.setValue(this.getInitialPoolSize());
				Property minPoolSize = new Property();
				minPoolSize.setName("minPoolSize");
				minPoolSize.setValue(this.getMinPoolSize());
				Property maxPoolSize = new Property();
				maxPoolSize.setName("maxPoolSize");
				maxPoolSize.setValue(this.getMaxPoolSize());
				Property maxStatements = new Property();
				maxStatements.setName("maxStatements");
				maxStatements.setValue(this.getMaxStatements());
				Property maxIdleTime = new Property();
				maxIdleTime.setName("maxIdleTime");
				maxIdleTime.setValue(this.getMaxIdleTime());
				propertys.add(user);
				propertys.add(password);
				propertys.add(jdbcUrl);
				propertys.add(driverClass);
				propertys.add(initialPoolSize);
				propertys.add(minPoolSize);
				propertys.add(maxPoolSize);
				propertys.add(maxStatements);
				propertys.add(maxIdleTime);
				bean.setProperty(propertys);
				beanList.add(bean);
			}
			beans.setBean(beanList);
			if (this.getIsShow())
				marshaller.marshal(beans, System.out);
			marshaller.marshal(beans, savePath);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}

	public String isShard() {
		return isShard;
	}

	public void setShard(String isShard) {
		this.isShard = isShard;
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(String initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public String getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(String minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public String getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(String maxStatements) {
		this.maxStatements = maxStatements;
	}

	public String getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(String maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getDataSourceIndex() {
		return dataSourceIndex;
	}

	public void setDataSourceIndex(int dataSourceIndex) {
		this.dataSourceIndex = dataSourceIndex;
	}

	public String getConsistent() {
		return consistent;
	}

	public void setConsistent(String consistent) {
		this.consistent = consistent;
	}

	public String getDbSize() {
		return dbSize;
	}

	public void setDbSize(String dbSize) {
		this.dbSize = dbSize;
	}

	public String getTbSuffix() {
		return tbSuffix;
	}

	public void setTbSuffix(String tbSuffix) {
		this.tbSuffix = tbSuffix;
	}
}