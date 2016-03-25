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
package com.sharksharding.sql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载sql配置文件中的sql信息
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class PropertyPlaceholderConfigurer {
	private Logger logger = LoggerFactory.getLogger(PropertyPlaceholderConfigurer.class);
	private Properties properties;
	private String path;

	private PropertyPlaceholderConfigurer(String path) {
		this.path = path;
		properties = new Properties();
		load();
	}

	/**
	 * 根据指定的路径加载sql配置文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @exception FileNotFoundException
	 * 
	 * @exception IOException
	 * 
	 * @return void
	 */
	public void load() {
		if (null != properties) {
			InputStream in = null;
			try {
				/* 检测是否需要从classpath下进行sql配置文件的读取 */
				if (path.indexOf("classpath:") != -1) {
					/* 从classpath下获取sql配置文件 */
					in = this.getClass().getResourceAsStream("/" + path.split("classpath:")[1]);
				}
				if (null == in) {
					/* 从文件路径获取sql配置文件 */
					in = new FileInputStream(path);
					properties.load(in);
				} else {
					properties.load(in);
				}
				logger.info("load sql file success");
			} catch (FileNotFoundException e) {
				throw new com.sharksharding.exception.FileNotFoundException(e.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 通过定义在配置文件中的持久层方法名称获取指定的sql信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @param key
	 *            sql配置文件的检索条件
	 * 
	 * @param routeKey
	 *            路由条件
	 * 
	 * @return String 指定的sql语句
	 */
	public String getSql(String key, long routeKey) {
		String sql = null;
		if (null != properties) {
			sql = properties.getProperty(key);
			sql = sql.replaceFirst("\\?", String.valueOf(routeKey));
		}
		return sql;
	}
}