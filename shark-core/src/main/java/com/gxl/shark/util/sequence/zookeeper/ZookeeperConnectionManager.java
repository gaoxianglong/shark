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
package com.gxl.shark.util.sequence.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxl.shark.exception.ResourceException;

/**
 * 客户端连接管理器,与zookeeper服务器建立session会话
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class ZookeeperConnectionManager {
	private static String zk_address;
	private static int zk_session_timeout;
	private static CountDownLatch countDownLatch;
	private static ZooKeeper zk_client;
	private static Logger logger = LoggerFactory.getLogger(ZookeeperConnectionManager.class);

	/**
	 * 初始化方法
	 *
	 * @author JohnGao
	 */
	public static void init(String zk_address, int zk_session_timeout) {
		setZk_address(zk_address);
		setZk_session_timeout(zk_session_timeout);
		countDownLatch = new CountDownLatch(1);
		connection();
	}

	/**
	 * 连接zookeeper
	 * 
	 * @author JohnGao
	 */
	private static void connection() {
		try {
			zk_client = new ZooKeeper(zk_address, zk_session_timeout, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					final KeeperState STATE = event.getState();
					final String VALUE = "与zookeeper配置中心[" + zk_address + "]";
					switch (STATE) {
					case SyncConnected:
						countDownLatch.countDown();
						logger.info(VALUE + "建立会话");
						break;
					case Disconnected:
						logger.warn(VALUE + "断开会话");
						break;
					case Expired:
						logger.error(VALUE + "建立的会话已经失效");
						break;
					case AuthFailed:
						logger.error(VALUE + "建立会话时ACL认证失败");
					default:
						break;
					}
				}
			});
			countDownLatch.await();
			setZk_client(zk_client);
		} catch (IOException e) {
			throw new ResourceException("与zookeeper建立会话失败[" + e.toString() + "]");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getZk_address() {
		return zk_address;
	}

	public static void setZk_address(String zk_address) {
		ZookeeperConnectionManager.zk_address = zk_address;
	}

	public static int getZk_session_timeout() {
		return zk_session_timeout;
	}

	public static void setZk_session_timeout(int zk_session_timeout) {
		ZookeeperConnectionManager.zk_session_timeout = zk_session_timeout;
	}

	public static ZooKeeper getZk_client() {
		return zk_client;
	}

	public static void setZk_client(ZooKeeper zk_client) {
		ZookeeperConnectionManager.zk_client = zk_client;
	}
}