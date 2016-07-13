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

/**
 * 对应数据库表[message_info]
 *
 * @author JohnGao
 */
@Component
public class Message {
	private int message_id;
	private long userinfo_test_id;
	private String message;

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public long getUserinfo_test_id() {
		return userinfo_test_id;
	}

	public void setUserinfo_test_id(long userinfo_test_id) {
		this.userinfo_test_id = userinfo_test_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}