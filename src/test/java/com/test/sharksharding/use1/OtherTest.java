package com.test.sharksharding.use1;
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
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:kratos5-context.xml")
//public class OtherTest {
//	private Logger logger = LoggerFactory.getLogger(OtherTest.class);
//
//	@Resource
//	private Message message;
//
//	@Resource
//	private MessageDao messageDao;
//
//	/**
//	 * 批量插入聊天记录
//	 * 
//	 * @author gaoxianglong
//	 */
//	public @Test void testInsert() {
//		try {
//			for (int i = 0; i < 100; i++) {
//				message.setMessage_id(i + 1);
//				message.setUserinfo_test_id(1234567890L);
//				message.setMessage("msg" + (i + 1));
//				messageDao.insertMessage(message);
//			}
//			logger.info("主表message数据插入成功...");
//		} catch (Exception e) {
//			logger.error("数据插入失败", e);
//		}
//	}
//
//	/**
//	 * 分页查询聊天记录
//	 *
//	 * @author gaoxianglong
//	 */
//	public @Test void testQuery() {
//		try {
//			List<Message> msgs = messageDao.queryMessagebyUid(1234567890L);
//			if (!msgs.isEmpty()) {
//				for (Message msg : msgs) {
//					System.out.println(msg.getMessage());
//				}
//			}
//		} catch (Exception e) {
//			logger.error("检索检索失败", e);
//		}
//	}
//}