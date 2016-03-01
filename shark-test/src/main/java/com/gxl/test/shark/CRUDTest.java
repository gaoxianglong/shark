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

import java.util.List;
import javax.annotation.Resource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gxl.shark.util.sequence.DbConnectionManager;
import com.gxl.shark.util.sequence.SequenceIDManger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:shark1-context.xml")
public class CRUDTest {
	@Resource(name="userDaoImpl")
	private UserDao userDao;
	@Resource
	private User user;
	final static String NAME = "root";
	final static String PWD = "cndmss_db";
	final static String URL = "jdbc:mysql://ip:3306/id";
	final static String DRIVER = "com.mysql.jdbc.Driver";

	/**
	 * 初始化SequenceId的数据源信息
	 *
	 * @author gaoxianglong
	 */
	public @BeforeClass static void init() {
		DbConnectionManager.init(NAME, PWD, URL, DRIVER);
	}

	/**
	 * insert测试，数据插入master
	 *
	 * @author gaoxianglong
	 */
	public @Test void testInsert() {
		try {
			long sequenceID = SequenceIDManger.getSequenceId(1, 1, 5000);
			System.out.println("sequenceID->" + sequenceID);
			user.setUserinfo_Id(sequenceID);
			user.setUsername("gaoxianglong");
			userDao.insertUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * select测试，从slave检索数据
	 *
	 * @author gaoxianglong
	 */
	public @Test void testQuery() {
		try {
			long sequenceID = 10100000000000001L;
			List<User> users = userDao.queryUserbyId(sequenceID);
			if (!users.isEmpty()) {
				User user = users.get(0);
				System.out.println("username->" + user.getUsername());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}