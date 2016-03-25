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
package com.sharksharding.resources.conn;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javax.annotation.Resource;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharksharding.exception.ConnectionException;
import com.sharksharding.exception.ResourceException;
import com.sharksharding.factory.RegisterNodeFactory;
import com.sharksharding.resources.register.zookeeper.node.RegisterNode;

/**
 * 客户端连接管理器,与zookeeper服务器建立session会话
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class ZookeeperConnectionManager {
	private String zk_address;
	private int zk_session_timeout;
	private CountDownLatch countDownLatch;
	private ZooKeeper zk_client;
	private String nodePath;
	private RegisterNode registerNode;
	private Logger logger = LoggerFactory.getLogger(ZookeeperConnectionManager.class);

	private ZookeeperConnectionManager(String zk_address, int zk_session_timeout, DataSourceBean dataSourceBean) {
		this(zk_address, zk_session_timeout, dataSourceBean.getNodePath());
	}

	private ZookeeperConnectionManager(String zk_address, int zk_session_timeout, String nodePath) {
		this.zk_address = zk_address;
		this.zk_session_timeout = zk_session_timeout;
		this.nodePath = nodePath;
		registerNode = RegisterNodeFactory.getRegisterNode();
		countDownLatch = new CountDownLatch(1);
	}

	/**
	 * 初始化方法
	 *
	 * @author JohnGao
	 */
	@SuppressWarnings("unused")
	private void init() {
		connection();
	}

	/**
	 * 连接zookeeper
	 * 
	 * @author JohnGao
	 */
	private void connection() {
		try {
			zk_client = new ZooKeeper(zk_address, zk_session_timeout, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					final KeeperState STATE = event.getState();
					switch (STATE) {
					case SyncConnected:
						countDownLatch.countDown();
						logger.info("connection zookeeper success");
						break;
					case Disconnected:
						logger.warn("zookeeper connection is disconnected");
						break;
					case Expired:
						logger.error("zookeeper session expired");
						break;
					case AuthFailed:
						logger.error("authentication failure");
					default:
						break;
					}
				}
			});
			countDownLatch.await();
			/* 注册与数据源相关的节点 */
			registerNode.register(zk_client, nodePath);
		} catch (IOException | InterruptedException e) {
			throw new ConnectionException(e.toString());
		}
	}
}