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

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.sharksharding.util.LoadVersion;

/**
 * 收集用户的常规数据信息,发送到gao_xianglong@sina.com,方便作者统计目前的shark活跃用户数量
 * 
 * @author gaoxianglong
 */
public class SendMail {
	private MailBean mailBean;
	private JavaMailSenderImpl mailSender;
	private SimpleMailMessage mailMessage;
	private Logger logger = LoggerFactory.getLogger(SendMail.class);

	public SendMail() {
		mailBean = new MailBean();
		mailSender = new JavaMailSenderImpl();
		mailSender.setPort(mailBean.getPORT());
		mailSender.setHost(mailBean.getHOST());
		mailSender.setUsername(mailBean.getUSERNAME());
		mailSender.setPassword(mailBean.getPASSWORD());
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		mailSender.setJavaMailProperties(properties);
		mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(mailBean.getFROM());
		mailMessage.setTo(mailBean.getTO());
		mailMessage.setSubject(mailBean.getSUBJECT());
		GetUserInfo getUserInfo = new GetUserInfo();
		mailMessage.setText("[version]:" + LoadVersion.getVersion() + "\n[javaVersion]:" + getUserInfo.getJavaVersion()
				+ "\n[osName]:" + getUserInfo.getOsName() + "\n[jvmName]:" + getUserInfo.getJvmName() + "\n[address]:"
				+ getUserInfo.getAddress() + "\n[hostName]:" + getUserInfo.getHostname() + "\n[startTime]:"
				+ getUserInfo.getStartTime());
	}

	/**
	 * 程序启动时发送邮件信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @exception Exception
	 * 
	 * @return void
	 */
	public void send() {
		new Thread() {
			@Override
			public void run() {
				try {
					mailSender.send(mailMessage);
					logger.debug("mail sending success");
				} catch (Exception e) {
					logger.debug("mail sending failed");
					e.printStackTrace();
				}
			}
		}.start();
	}
}