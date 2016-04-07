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
import com.sharksharding.factory.SetValueFactory;

/**
 * 字段注解解析器，用于解析标记了@Column的字段
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public class FieldResolver extends SharkAnnotationResolverImpl {
	private SetValue setValue = SetValueFactory.getSetValue();

	@Override
	public <T> void fieldResolver(T goalObject, ResultSet rs) {
		AutoColumn autoColumn = null;
		/* 检测目标对象是否包含@AutoColumn注解 */
		if (goalObject.getClass().isAnnotationPresent(AutoColumn.class)) {
			autoColumn = goalObject.getClass().getAnnotation(AutoColumn.class);
		}
		/* 迭代目标对象的所有字段 */
		for (Field goalObjectField : goalObject.getClass().getDeclaredFields()) {
			/* 检测目标对象的指定字段是否包含@Column注解 */
			if (goalObjectField.isAnnotationPresent(Column.class)) {
				/* 获取目标对象的@Column注解属性 */
				Column goalObjectColumn = goalObjectField.getAnnotation(Column.class);
				final String name = goalObjectColumn.name();
				/* 如果找不到注解就从类名映射 */
				if (null != name && 0 < name.length()) {
					setValue.set(goalObjectField, goalObject, rs, name);
				} else {
					setValue.set(goalObjectField, goalObject, rs, goalObjectField.getName());
				}
			} else if (null != autoColumn && autoColumn.value()) {
				setValue.set(goalObjectField, goalObject, rs, goalObjectField.getName());
			}
		}
	}
}