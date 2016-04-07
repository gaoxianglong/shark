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
package com.sharksharding.util.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sharksharding.exception.MapperException;

/**
 * 字段赋值操作
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public class SetValue {
	/**
	 * 执行参数自动赋值
	 * 
	 * @author JohnGao
	 * 
	 * @param goalObjectField
	 *            反射目标对象字段
	 * 
	 * @param goalObject
	 *            目标对象, 即需要被自动赋值的对象
	 * 
	 * @param ResultSet
	 * 
	 * @param columnName
	 *            字段名称
	 * 
	 * @exception MapperException
	 * 
	 * @return void
	 */
	public <T> void set(Field goalObjectField, T goalObject, ResultSet rs, String columnName) {
		try {
			/* 允许向目标对象的私有字段进行赋值操作 */
			goalObjectField.setAccessible(true);
			/* 将源对象字段值赋值于目标对象字段 */
			goalObjectField.set(goalObject, rs.getObject(columnName));
		} catch (SQLException e) {
			throw new MapperException("Column '" + columnName + "' not found");
		} catch (Exception e) {
			throw new MapperException("mapper fail");
		}
	}
}