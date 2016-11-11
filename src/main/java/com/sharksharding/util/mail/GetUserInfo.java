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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 申明:收集的数据信息不会用作其他用途,更不会外泄,仅仅只是统计目前shark的活跃用户数量
 * 
 * 收集用户的常规数据信息(包括:ADDRESS、HOSTNAME等)
 * 
 * @author gaoxianglong
 */
public class GetUserInfo {
	private String address;
	private String hostname;
	private String startTime;
	private String osName;
	private String jvmName;
	private String javaVersion;
	private Logger logger = LoggerFactory.getLogger(GetUserInfo.class);

	protected GetUserInfo() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			hostname = inetAddress.getHostName();
			address = inetAddress.getHostAddress();
			osName = System.getProperty("os.name");
			jvmName = System.getProperty("java.vm.name");
			javaVersion = System.getProperty("java.version");
			startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		} catch (UnknownHostException e) {
			logger.debug("get address failed");
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getJvmName() {
		return jvmName;
	}

	public void setJvmName(String jvmName) {
		this.jvmName = jvmName;
	}

	public String getJavaVersion() {
		return javaVersion;
	}

	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
}