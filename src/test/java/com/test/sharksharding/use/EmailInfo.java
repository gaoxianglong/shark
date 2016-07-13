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
package com.test.sharksharding.use;

import org.springframework.stereotype.Component;

import com.sharksharding.util.mapper.AutoColumn;
import com.sharksharding.util.mapper.Column;
import com.sharksharding.util.mapper.Mapper;

/**
 * 对应数据库表[email_test]
 *
 * @author JohnGao
 */

@Mapper
@Component
public class EmailInfo {
	@Column(name = "email")
	private String email;
	@Column
	private long email_hash;
	@Column
	private long uid;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

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
}