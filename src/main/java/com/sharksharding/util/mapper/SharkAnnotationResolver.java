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

/**
 * shark注解解析器接口
 *
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public interface SharkAnnotationResolver {
	/**
	 * 字段注解解析
	 * 
	 * @author JohnGao
	 * 
	 * @param goalObject
	 *            目标对象, 即需要被自动赋值的对象
	 * 
	 * @param ResultSet
	 * 
	 * @exception MapperException
	 * 
	 * @return void
	 */
	public <T> void fieldResolver(T goalObject, ResultSet rs);

	/**
	 * 类型注解解析
	 * 
	 * @author JohnGao
	 * 
	 * @param goalObject
	 *            目标对象, 即需要被自动赋值的对象
	 * 
	 * @return boolean 解析结果
	 */
	public <T> boolean classResolver(T goalObject);
}