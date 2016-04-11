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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sharksharding.exception.XmlResolveException;

/**
 * 动态拼接sql的模板类
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public class SQLTemplate {
	private String path;
	private Map<String, String> sqlMap;
	private final String xpathExpression = "//sql";
	private final String name = "name";
	private Logger logger = LoggerFactory.getLogger(SQLTemplate.class);

	private SQLTemplate(String path) {
		this.path = path;
		sqlMap = new ConcurrentHashMap<String, String>();
		load();
	}

	/**
	 * 根据指定的路径加载sql.xml模板文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @exception FileNotFoundException
	 * 
	 * @exception IOException
	 * 
	 * @return void
	 */
	private void load() {
		if (null != path) {
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
					resolveXml(in);
				} else {
					resolveXml(in);
				}
				logger.info("load sql file success");
			} catch (FileNotFoundException e) {
				throw new com.sharksharding.exception.FileNotFoundException(e.toString());
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
	 * 使用dom4j解析xml文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @param in
	 *            字节输入流
	 * 
	 * @exception XmlResolveException
	 * 
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	private void resolveXml(InputStream in) {
		if (null == in)
			return;
		String sql = null;
		Document document = null;
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(in);
		} catch (DocumentException e) {
			throw new XmlResolveException("xml resolve fail");
		}
		Element root = document.getRootElement();
		List<Element> elements = root.selectNodes(xpathExpression);
		if (!elements.isEmpty()) {
			for (Element element : elements) {
				sql = element.attribute(name).getValue();
				sqlMap.put(sql, element.getText());
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
	 * @param params
	 *            参数集合
	 * 
	 * @return String 指定的sql语句
	 */
	public String getSql(String key, Map<String, ?> params) {
		final String sql = RenderSQLTemplate.render(sqlMap.get(key), params);
		/* 验证SQL语句WHERE条件后面是否带参数 */
		return SQLIsWhereColumn.isColumn(sql) ? sql : null;
	}
}