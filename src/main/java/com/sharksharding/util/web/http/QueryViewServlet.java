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
package com.sharksharding.util.web.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.sharksharding.core.shard.SharkJdbcTemplate;
import com.sharksharding.exception.FileNotFoundException;

/**
 * shark的sql执行视图控制器
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class QueryViewServlet extends HttpServlet {
	private static Logger logger = LoggerFactory.getLogger(QueryViewServlet.class);
	private final String PATH = "web/http/resources/";
	private static final long serialVersionUID = 3561221118511195264L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 设置字符编码 */
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		/* 加载Ioc容器 */
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession().getServletContext());
		SharkJdbcTemplate jdbcTemplate = (SharkJdbcTemplate) context.getBean("jdbcTemplate");
		if (null != jdbcTemplate) {
			byte[] viewData = null;
			byte[] responseData = null;
			/* 1为跳转页面操作、2为获取数据操作 */
			final String TYPE = request.getParameter("type");
			if (null == TYPE) {
				viewData = initView(PATH + "index.html");
				if (null != viewData) {
					write(response, viewData);
				}
			} else {
				switch (Integer.parseInt(TYPE)) {
				case 1:
					String page = request.getParameter("page");
					if (page.equals("index")) {
						viewData = initView(PATH + "index.html");
					} else if (page.equals("query")) {
						viewData = initView(PATH + "query.html");
					}
					if (null != viewData) {
						write(response, viewData);
					}
					break;
				case 2:
					final String SQL = request.getParameter("sql");
					if (null != SQL && 0 < SQL.trim().length()) {
						JSONObject jsonObj = new JSONObject();
						ExecuteSql exeSql = new ExecuteSql(jdbcTemplate);
						try {
							Map<String, Object> datas = exeSql.queryData(SQL);
							if (!datas.isEmpty()) {
								StringBuffer strBuffer = new StringBuffer();
								Set<String> keys = datas.keySet();
								for (String key : keys) {
									strBuffer.append(datas.get(key) + ",");
								}
								jsonObj.put("sqlResult", strBuffer.toString());
								responseData = jsonObj.toJSONString().getBytes();
							}
						} catch (Exception e) {
							Writer write = new StringWriter();
							e.printStackTrace(new PrintWriter(write));
							jsonObj.put("error", write.toString());
							responseData = jsonObj.toString().getBytes("utf-8");
						}
					} else {
						responseData = GetIndexData.getData(jdbcTemplate).getBytes("utf-8");
					}
					if (null != responseData) {
						write(response, responseData);
					}
				}
			}
		}
	}

	/**
	 * 封装输出
	 * 
	 * @author gaoxianglong
	 * 
	 * @param response
	 *            http响应请求
	 * 
	 * @param data
	 *            响应数据
	 * 
	 * @throws IOException
	 * 
	 * @return void
	 */
	public void write(HttpServletResponse response, byte[] data) throws IOException {
		response.getOutputStream().write(data);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	/**
	 * 初始化静态页面
	 * 
	 * @author gaoxianglong
	 * 
	 * @param path
	 *            配置文件相对路径
	 * 
	 * @return byte[] 页面数据内容
	 */
	protected byte[] initView(String path) {
		byte[] value = null;
		BufferedInputStream reader = null;
		try {
			reader = new BufferedInputStream(QueryViewServlet.class.getClassLoader().getResourceAsStream(path));
			value = new byte[reader.available()];
			reader.read(value);
		} catch (IOException e) {
			throw new FileNotFoundException("can not find config");
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}