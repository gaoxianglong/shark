package com.test.sharksharding.use1;
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

import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.sharksharding.util.sequence.SequenceIDManger;

@RunWith(SpringJUnit4ClassRunner.class)
/* 片名连续的库内分片配置 */
// @ContextConfiguration(locations = "classpath*:shark2-context.xml")
/* 非片名连续的库内分片配置 */
// @ContextConfiguration(locations = "classpath*:shark3-context.xml")
/* 片名连续的一库一片配置 */
// @ContextConfiguration(locations = "classpath*:shark4-context.xml")
/* 非片名连续的一库一片配置 */
@ContextConfiguration(locations = "classpath*:shark5-context.xml")
public class Main {
	@Resource
	private UserDao userDao;
	@Resource
	private EmailDao emailDao;
	@Resource
	private UserInfo user;
	@Resource
	private EmailInfo emailInfo;
	final static String NAME = "root";
	final static String PWD = "cndmss_db";
	final static String URL = "jdbc:mysql://120.24.75.22:3306/id";
	final static String DRIVER = "com.mysql.jdbc.Driver";
	private static long sequenceid;
	private static String email = UUID.randomUUID().toString();
	private static Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * 初始化SequenceId的数据源信息
	 * 
	 * @author gaoxianglong
	 */
	public @BeforeClass static void init() {
		SequenceIDManger.init(NAME, PWD, URL, DRIVER);
		sequenceid = SequenceIDManger.getSequenceId(1, 1, 5000);
		logger.info("sequenceid-->" + sequenceid);
	}

	/**
	 * 插入反向索引表和主表
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testInsert() {
		try {
			emailInfo.setEmail(email);
			emailInfo.setEmail_hash(Math.abs(emailInfo.getEmail().hashCode()));
			emailInfo.setUid(sequenceid);
			emailDao.setEmail(emailInfo);
			logger.info("insert table:email_test success");
			user.setUserName("gaoxianglong");
			user.setUid(sequenceid);
			userDao.setUserInfo(user);
			logger.info("insert table:userinfo_test success");
		} catch (Exception e) {
			logger.error("insert data fail", e);
		}
	}

	/**
	 * 通过反向索引表检索出主表的路由条件
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testQuery() {
		try {
			emailInfo.setEmail(email);
			emailInfo.setEmail_hash(Math.abs(emailInfo.getEmail().hashCode()));
			List<EmailInfo> emails = emailDao.getEmail(emailInfo);
			if (!emails.isEmpty()) {
				EmailInfo email = emails.get(0);
				long uid = email.getUid();
				System.out.println("uid-->" + uid);
				List<UserInfo> users = userDao.getUserInfo(uid);
				if (!users.isEmpty()) {
					UserInfo user = users.get(0);
					System.out.println("userName-->" + user.getUserName());
				}
			}
		} catch (Exception e) {
			logger.error("query data fail", e);
		}
	}
}