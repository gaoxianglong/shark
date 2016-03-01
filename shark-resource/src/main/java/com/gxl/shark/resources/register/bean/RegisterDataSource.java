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
package com.gxl.shark.resources.register.bean;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import com.alibaba.druid.pool.DruidDataSource;
import com.gxl.shark.core.shard.GetJdbcTemplate;
import com.gxl.shark.core.shard.SharkJdbcTemplate;
import com.gxl.shark.exception.ResourceException;
import com.gxl.shark.resources.zookeeper.DataSourceBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 动态向spring的ioc容器中注册bean实例实现
 * 
 * @author gaoxianglong
 */
@Component
public class RegisterDataSource implements RegisterBean {
	public ApplicationContext aContext;
	private Logger logger = LoggerFactory.getLogger(RegisterDataSource.class);

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.aContext = applicationContext;
	}

	@Override
	public void register(String nodePathValue, DataSourceBean dataSourceBean) {
		final String tmpdir = System.getProperty("java.io.tmpdir") + "shark-info.xml";
		try (BufferedWriter out = new BufferedWriter(new FileWriter(tmpdir))) {
			if (null != nodePathValue) {
				logger.debug("从zookeeper配置中心中获取的配置信息存储位置-->" + tmpdir);
				out.write(nodePathValue);
				out.flush();
				FileSystemResource resource = new FileSystemResource(tmpdir);
				ConfigurableApplicationContext cfgContext = (ConfigurableApplicationContext) aContext;
				DefaultListableBeanFactory beanfactory = (DefaultListableBeanFactory) cfgContext.getBeanFactory();
				/* 验证jdbcTemplate是否已经存在 */
				if (beanfactory.isBeanNameInUse("jdbcTemplate")) {
					beanfactory.removeBeanDefinition("jdbcTemplate");
					logger.debug("从ioc容器中删除bean-->jdbcTemplate");
				}
				/* 将配置中心获取的配置信息与当前上下文中的ioc容器进行合并 */
				new XmlBeanDefinitionReader(beanfactory).loadBeanDefinitions(resource);
				/* 替换jdbcTemplate引用 */
				GetJdbcTemplate.setSharkJdbcTemplate((SharkJdbcTemplate) beanfactory.getBean("jdbcTemplate"));
			}
		} catch (Exception e) {
			throw new ResourceException("zookeeper配置中心发生错误[" + e.toString() + "]");
		}
	}

	/**
	 * 关闭连接池中持有的数据库连接,在连接池中设置属性destroy-method="close"便可自动关闭连接池所持有的数据库连接
	 * 
	 * @author gaoxianglong
	 * 
	 * @param connPoolType
	 *            连接池类型,缺省为0即ComboPooledDataSource,反之为DruidDataSource
	 * 
	 * @param beanfactory
	 * 
	 * @param num
	 * 
	 * @return void
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void close(int connPoolType, DefaultListableBeanFactory beanfactory, int num) {
		final String beanName = "dataSource" + num;
		if (beanfactory.isBeanNameInUse(beanName)) {
			if (0 == connPoolType) {
				ComboPooledDataSource dataSource = (ComboPooledDataSource) beanfactory.getBean(beanName);
				dataSource.close();
			} else {
				DruidDataSource dataSource = (DruidDataSource) beanfactory.getBean(beanName);
				dataSource.close();
			}
			logger.debug("关闭dataSource" + num + "所持有的数据库连接");
		}
	}
}