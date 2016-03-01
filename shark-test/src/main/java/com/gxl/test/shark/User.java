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
package com.gxl.test.shark;

import org.springframework.stereotype.Component;

/**
 * 对应数据库表[userinfo_test]
 *
 * @author JohnGao
 */
@Component
public class User {
	private long userinfo_Id;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getUserinfo_Id() {
		return userinfo_Id;
	}

	public void setUserinfo_Id(long userinfo_Id) {
		this.userinfo_Id = userinfo_Id;
	}
}