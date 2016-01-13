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
 * See the License for the specific language governing permissions AND
 * limitations under the License.
 */
package com.gxl.shark.shard;

import org.junit.Test;

/**
 * 路由规则测试类
 * 
 * @author gaoxianglong
 */
public class SetTabNameTest {
	/**
	 * 采用 replaceFirst()方法替换第一次出现的表名
	 * 
	 * @author gaoxianglong
	 */
	public @Test void setName() {
		final String TABLE_NAME = "userinfo";
		final String SQL1 = "INSERT INTO " + TABLE_NAME + "(userinfo_id,sex) VALUES(?,?)";
		System.out.println(SQL1.replaceFirst(TABLE_NAME, TABLE_NAME + "_0010"));
		final String SQL2 = "UPDATE " + TABLE_NAME + " SET sex=? WHERE userinfo_id = ?";
		System.out.println(SQL2.replaceFirst(TABLE_NAME, TABLE_NAME + "_0010"));
		final String SQL3 = "DELETE FROM " + TABLE_NAME + " WHERE userinfo_id = ?";
		System.out.println(SQL3.replaceFirst(TABLE_NAME, TABLE_NAME + "_0010"));
		final String SQL4 = "SELECT sex FROM " + TABLE_NAME + " where userinfo_id = ?";
		System.out.println(SQL4.replaceFirst(TABLE_NAME, TABLE_NAME + "_0010"));
	}
}