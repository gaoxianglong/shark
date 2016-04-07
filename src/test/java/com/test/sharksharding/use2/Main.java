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
package com.test.sharksharding.use2;

import java.util.List;
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
@ContextConfiguration(locations = "classpath*:shark1-context.xml")
public class Main {
	final static String NAME = "root";
	final static String PWD = "cndmss_db";
	final static String URL = "jdbc:mysql://120.24.75.22:3306/id";
	final static String DRIVER = "com.mysql.jdbc.Driver";
	@Resource(name = "userDaoImpl")
	private UserDao userDao;
	@Resource
	private UserInfo user;
	private static long sequenceid;
	private static Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * 初始化SequenceId的数据源信息
	 *
	 * @author gaoxianglong
	 */
	public @BeforeClass static void init() {
		SequenceIDManger.init(NAME, PWD, URL, DRIVER);
		/* 生产建议memData设置为5000 */
		sequenceid = SequenceIDManger.getSequenceId(1, 1, 100);
		logger.info("sequenceid-->" + sequenceid);
	}

	/**
	 * 写入测试
	 *
	 * @author gaoxianglong
	 */
	public @Test void testInsert() {
		try {
			user.setUid(sequenceid);
			user.setUserName("gaoxianglong");
			userDao.setUserInfo(user);
			logger.info("insert success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拉取测试
	 *
	 * @author gaoxianglong
	 */
	public @Test void testQuery() {
		try {
			List<UserInfo> users = userDao.getUserInfo(sequenceid);
			if (!users.isEmpty()) {
				UserInfo user = users.get(0);
				logger.info("userName-->" + user.getUserName());
			}
		} catch (Exception e) {
			logger.error("query data fail", e);
		}
	}

	/**
	 * 拉取所有测试
	 *
	 * @author gaoxianglong
	 */
	public @Test void testQuerys() {
		try {
			List<UserInfo> users = userDao.getUserInfos();
			if (!users.isEmpty()) {
				for (UserInfo user : users) {
					logger.info("uid-->" + user.getUid() + "\tuserName-->" + user.getUserName());
				}
			}
		} catch (Exception e) {
			logger.error("query data fail", e);
		}
	}
}