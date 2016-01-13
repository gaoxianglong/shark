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
package com.gxl.shark.test.core.shard;

import org.springframework.stereotype.Component;

/**
 * 对应数据库表[email_index]
 *
 * @author JohnGao
 */
@Component
public class Email {
	private String email;
	private long email_hash;
	private long userinfo_Id;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getEmail_hash() {
		return email_hash;
	}

	public void setEmail_hash(long email_hash) {
		this.email_hash = email_hash;
	}

	public long getUserinfo_Id() {
		return userinfo_Id;
	}

	public void setUserinfo_Id(long userinfo_Id) {
		this.userinfo_Id = userinfo_Id;
	}
}