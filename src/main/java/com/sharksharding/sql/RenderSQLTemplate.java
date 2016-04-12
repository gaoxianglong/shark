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

import java.io.StringWriter;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import com.sharksharding.exception.RenderException;

/**
 * 渲染sql.xml模板内容
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public class RenderSQLTemplate {
	static {
		try {
			Velocity.init();
		} catch (Exception e) {
			throw new RenderException("velocity init fail");
		}
	}

	/**
	 * 执行sql.xml内容渲染
	 * 
	 * @param sql
	 *            模板内容
	 * 
	 * @param model
	 *            结果集
	 * 
	 * @exception RenderException
	 * 
	 * @return String 渲染后的sql语句
	 */
	public static String render(String template, Map<String, ?> model) {
		String sql = null;
		try {
			VelocityContext velocityContext = new VelocityContext(model);
			StringWriter result = new StringWriter();
			Velocity.evaluate(velocityContext, result, "", template);
			sql = result.toString().replaceAll("\n", "").replaceAll("\t", "");
		} catch (Exception e) {
			throw new RenderException("render fail");
		}
		return sql;
	}
}