///*
// * Copyright 2015-2101 gaoxianglong
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.test.sharksharding.use.shard2;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import com.sharksharding.util.sequence.SequenceIDManger;
//import com.test.sharksharding.use.shard1.User;
//import com.test.sharksharding.use.shard1.UserDao;
//
//@RunWith(SpringJUnit4ClassRunner.class)
///* 片名连续的库内分片配置 */
//// @ContextConfiguration(locations = "classpath*:shark2-context.xml")
///* 非片名连续的库内分片配置 */
//// @ContextConfiguration(locations = "classpath*:shark3-context.xml")
///* 片名连续的一库一片配置 */
//// @ContextConfiguration(locations = "classpath*:shark4-context.xml")
///* 非片名连续的一库一片配置 */
//@ContextConfiguration(locations = "classpath*:shark5-context.xml")
//public class CRUDTest {
//	private Logger logger = LoggerFactory.getLogger(CRUDTest.class);
//	@Resource
//	private UserDao userDao;
//	@Resource
//	private EmailDao emailDao;
//	@Resource
//	private User user;
//	@Resource
//	private Email email;
//	final static String NAME = "root";
//	final static String PWD = "cndmss_db";
//	final static String URL = "jdbc:mysql://120.24.75.22:3306/id";
//	final static String DRIVER = "com.mysql.jdbc.Driver";
//
//	/**
//	 * 初始化SequenceId的数据源信息
//	 * 
//	 * @author gaoxianglong
//	 */
//	public @BeforeClass static void init() {
//		SequenceIDManger.init(NAME, PWD, URL, DRIVER);
//	}
//
//	/**
//	 * 插入反向索引表和主表
//	 * 
//	 * @author gaoxianglong
//	 */
//	public @Test void testInsert() {
//		try {
//			long sequenceID = SequenceIDManger.getSequenceId(1, 1, 5000);
//			System.out.println("sequenceID->" + sequenceID);
//			email.setEmail("gaoxianglong131412@test.com");
//			email.setEmail_hash(Math.abs(email.getEmail().hashCode()));
//			email.setUserinfo_Id(sequenceID);
//			emailDao.insertEmail(email);
//			logger.info("反向索引表email_hash数据插入成功...");
//			user.setUsername("gao123");
//			user.setUserinfo_Id(sequenceID);
//			userDao.insertUser(user);
//			logger.info("主表userinfo数据插入成功...");
//		} catch (Exception e) {
//			logger.error("数据插入失败", e);
//		}
//	}
//
//	/**
//	 * 通过反向索引表检索出主表的路由条件
//	 * 
//	 * @author gaoxianglong
//	 */
//	public @Test void testQuery() {
//		try {
//			email.setEmail("gaoxianglong131412@test.com");
//			email.setEmail_hash(Math.abs(email.getEmail().hashCode()));
//			List<Email> emails = emailDao.queryEmailbyId(email);
//			if (!emails.isEmpty()) {
//				Email email = emails.get(0);
//				long uid = email.getUserinfo_Id();
//				System.out.println("uid->" + uid);
//				List<User> users = userDao.queryUserbyId(uid);
//				if (!users.isEmpty()) {
//					User user = users.get(0);
//					System.out.println("userName->" + user.getUsername());
//				}
//			}
//		} catch (Exception e) {
//			logger.error("检索检索失败", e);
//		}
//	}
//}