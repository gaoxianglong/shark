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
package com.test.sharksharding.util.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.sharksharding.util.sequence.SequenceIDManger;
import com.sharksharding.util.sequence.zookeeper.CreateZookeeperSequenceId;

/**
 * 获取zookeeper生成的SequenceId测试类
 * 
 * @author gaoxianglong
 */
public class GetZookeeperSequenceIdTest {
	final static String ADDRESS = "120.25.58.116:2181,120.25.58.116:2182,120.25.58.116:2183,120.25.58.116:2184";
	final static int TIMEOUT = 30000;
	final String PATH = "/root";

	/**
	 * 初始化数据源信息
	 * 
	 * @author gaoxianglong
	 */
	public @BeforeClass static void init() {
		SequenceIDManger.init(ADDRESS, TIMEOUT);
	}

	/**
	 * 获取SequenceId
	 * 
	 * @author gaoxianglong
	 */
	public @Test void getSequenceId() {
		System.out.println(SequenceIDManger.getSequenceId(PATH, 100, 100000, 5000));
	}

	/**
	 * 批量获取SequenceId
	 * 
	 * @author gaoxianglong
	 */
	public @Test void getSequenceId2() {
		for (int i = 0; i < 1000; i++) {
			System.out.println(SequenceIDManger.getSequenceId(PATH, 100, 100000, 5000));
		}
	}

	/**
	 * 并发批量获取SequenceId
	 * 
	 * @author gaoxianglong
	 */
	public @Test void getSequenceId3() {
		final CountDownLatch count = new CountDownLatch(2);
		final List<Long> id1 = new ArrayList<Long>();
		final List<Long> id2 = new ArrayList<Long>();
		final int size = 10000;
		new Thread() {
			public void run() {
				for (int i = 0; i < size; i++) {
					id1.add(SequenceIDManger.getSequenceId(PATH, 100, 100000, 5000));
				}
				count.countDown();
			}
		}.start();
		new Thread() {
			public void run() {
				for (int i = 0; i < size; i++) {
					id2.add(SequenceIDManger.getSequenceId(PATH, 100, 200000, 5000));
				}
				count.countDown();
			}
		}.start();
		try {
			count.await();
			Assert.assertFalse(id1.containsAll(id2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}