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
package com.sharksharding.util.mail;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * 邮件信息参数设置
 * 
 * @author gaoxianglong
 */
public class MailBean {
	private final int PORT = 25;
	private final String HOST = "smtp.sina.com";
	private final String USERNAME = "shark_report@sina.com";
	private final String PASSWORD = "AvebH9tPmNDD5x7MCOXjqOPg17SUx14UmUZLmqheoUTOmvgD4pK2kwVI6LWunMRZsNZKAVGrcxr4vpfYSsWEpw==";
	private final String FROM = "shark_report@sina.com";
	private final String TO = "gao_xianglong@sina.com";
	private final String SUBJECT = "shark用户常规信息收集";

	public int getPORT() {
		return PORT;
	}

	public String getHOST() {
		return HOST;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	@SuppressWarnings("static-access")
	public String getPASSWORD() {
		ConfigTools configTools = new ConfigTools();
		String decrypt_password = null;
		try {
			decrypt_password = configTools.decrypt(PASSWORD);
		} catch (Exception e) {
			// ...
		}
		return decrypt_password;
	}

	public String getFROM() {
		return FROM;
	}

	public String getTO() {
		return TO;
	}

	public String getSUBJECT() {
		return SUBJECT;
	}
}