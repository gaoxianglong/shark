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
package com.sharksharding.resources.register.bean;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sharksharding.core.shard.GetJdbcTemplate;
import com.sharksharding.exception.RegisterBeanException;
import com.sharksharding.exception.SharkRuntimeException;
import com.sharksharding.util.TmpManager;

/**
 * 动态向spring的ioc容器中注册bean实例实现
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class RegisterDataSource implements RegisterBean {
	private static ApplicationContext aContext;
	private static Logger logger = LoggerFactory.getLogger(RegisterDataSource.class);

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.aContext = applicationContext;
	}

	/**
	 * 通过指定的beanName从spring的ioc容器中获取JdbcTemplate实例
	 * 
	 * @author gaoxianglong
	 * 
	 * @param beanName
	 * 
	 * @return JdbcTemplate
	 */
	public static JdbcTemplate getBean(String beanName) {
		if (null != beanName) {
			/* 检查ioc容器中是否包含指定beanName的实例 */
			if (aContext.containsBean(beanName)) {
				Object obj = null;
				obj = aContext.getBean(beanName);
				if (obj instanceof JdbcTemplate) {
					return (JdbcTemplate) obj;
				} else {
					throw new SharkRuntimeException("beanName " + beanName + " is not an instance of JdbcTemplate");
				}
			} else {
				logger.warn("beanName " + beanName + " not found");
			}
		}
		return null;
	}

	/**
	 * 注册bean
	 * 
	 * @author gaoxianglong
	 * 
	 * @param nodePathValue
	 *            zookeeper注册中心的节点value
	 * 
	 * @param resourceType
	 *            注册中心类型
	 * 
	 * @return void
	 */
	public static void register(String nodePathValue, String resourceType) {
		if (null == aContext)
			return;
		final String tmpdir = TmpManager.createTmp();
		logger.debug("tmpdir-->" + tmpdir);
		try (BufferedWriter out = new BufferedWriter(new FileWriter(tmpdir))) {
			if (null != nodePathValue) {
				out.write(nodePathValue);
				out.flush();
				FileSystemResource resource = new FileSystemResource(tmpdir);
				ConfigurableApplicationContext cfgContext = (ConfigurableApplicationContext) aContext;
				DefaultListableBeanFactory beanfactory = (DefaultListableBeanFactory) cfgContext.getBeanFactory();
				/*
				 * 将配置中心获取的配置信息与当前上下文中的ioc容器进行合并,不需要手动移除之前的bean,
				 * 调用loadBeanDefinitions()方法时会进行自动移除
				 */
				new XmlBeanDefinitionReader(beanfactory).loadBeanDefinitions(resource);
				final String defaultBeanName = "jdbcTemplate";
				String[] beanNames = beanfactory.getBeanDefinitionNames();
				for (String beanName : beanNames) {
					/* 替换上下文中缺省beanName为jdbcTemplate的JdbcTemplate的引用 */
					if (defaultBeanName.equals(beanName)) {
						GetJdbcTemplate.setJdbcTemplate((JdbcTemplate) beanfactory.getBean(defaultBeanName));
					} else {
						/* 实例化所有所有未实例化的bean */
						beanfactory.getBean(beanName);
					}
				}
			}
		} catch (Exception e) {
			throw new RegisterBeanException(e.toString());
		} finally {
			TmpManager.deleteTmp(tmpdir);
		}
	}
}