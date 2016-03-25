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
package com.sharksharding.resources.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharksharding.exception.ConnectionException;
import com.sharksharding.exception.ResourceException;
import com.sharksharding.resources.conn.DataSourceBean;
import com.sharksharding.resources.register.bean.RegisterDataSource;

/**
 * sharding、数据源相关的zookeeper节点watcher
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class ZookeeperWatcher implements Watcher {
	private ZooKeeper zk_client;
	private DataSourceBean dataSourceBean;
	private String nodePath;
	private Logger logger = LoggerFactory.getLogger(ZookeeperWatcher.class);

	public void init(ZooKeeper zk_client, DataSourceBean dataSourceBean) {
		init(zk_client, dataSourceBean.getNodePath());
	}

	public void init(ZooKeeper zk_client, String nodePath) {
		this.zk_client = zk_client;
		this.nodePath = nodePath;
	}

	@Override
	public void process(WatchedEvent event) {
		if (null == zk_client)
			return;
		try {
			Thread.sleep(100);
			/* 重新注册节点 */
			zk_client.exists(nodePath, this);
			EventType eventType = event.getType();
			switch (eventType) {
			case NodeCreated:
				logger.info("create node-->" + event.getPath());
				break;
			case NodeDataChanged:
				final String nodePathValue = new String(zk_client.getData(nodePath, false, null));
				RegisterDataSource.register(nodePathValue, "zookeeper");
				logger.info("change node data-->" + event.getPath());
				break;
			case NodeChildrenChanged:
				break;
			case NodeDeleted:
			default:
				break;
			}
		} catch (Exception e) {
			throw new ConnectionException(e.toString());
		}
	}
}