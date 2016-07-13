package com.test.sharksharding.use;
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

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
/* 多库多表模式 */
@ContextConfiguration(locations = "classpath*:shark2-context.xml")
/* 单库多表模式 */
//@ContextConfiguration(locations = "classpath*:shark3-context.xml")
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
	final static String PWD = "";
	final static String URL = "jdbc:mysql://ip:port/test_id";
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
		sequenceid = SequenceIDManger.getSequenceId(100, 10, 100);
		logger.info("sequenceid-->" + sequenceid);
	}

	/**
	 * 插入反向索引表和主表
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testInsert() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("email", email);
			params.put("email_hash", Math.abs(email.hashCode()));
			params.put("uid", sequenceid);
			emailDao.setEmail(params);
			logger.info("insert table:email_test success");
			params = new HashMap<String, Object>();
			params.put("uid", sequenceid);
			params.put("userName", "gaoxianglong");
			userDao.setUserInfo(params);
			logger.info("insert table:userinfo_test success");
			testQuery();
		} catch (Exception e) {
			logger.error("insert data fail", e);
		}
	}

	/**
	 * 通过反向索引表检索出主表的路由条件
	 * 
	 * @author gaoxianglong
	 */
	public void testQuery() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("email", email);
			params.put("email_hash", Math.abs(email.hashCode()));
			List<EmailInfo> emails = emailDao.getEmail(params);
			if (!emails.isEmpty()) {
				EmailInfo email = emails.get(0);
				long uid = email.getUid();
				System.out.println("uid-->" + uid);
				params = new HashMap<String, Object>();
				params.put("uid", sequenceid);
				List<UserInfo> users = userDao.getUserInfo(params);
				if (!users.isEmpty()) {
					UserInfo user = users.get(0);
					System.out.println("userName-->" + user.getUserName());
				}
			}
		} catch (Exception e) {
			logger.error("query data fail", e);
		}
	}

	/**
	 * 更新主表数据
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testUpdate() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", sequenceid);
			params.put("userName", "gaoxianglong7777");
			userDao.changeUserInfo(params);
			logger.info("update table:userinfo_test success");
		} catch (Exception e) {
			logger.error("update data fail", e);
		}
	}
}