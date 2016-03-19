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

import javax.annotation.Resource;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sharksharding.exception.ResourceException;
import com.sharksharding.resources.conn.DataSourceBean;
import com.sharksharding.resources.register.bean.RegisterBean;

/**
 * sharding、数据源相关的zookeeper节点watcher
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
@Component
public class ZookeeperWatcher implements Watcher {
	@Resource(name = "registerDataSource")
	private RegisterBean registerBean;
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
			final String VALUE = "zookeeper配置中心";
			switch (eventType) {
			case NodeCreated:
				logger.info(VALUE + "节点[" + event.getPath() + "]被创建");
				break;
			case NodeDataChanged:
				String nodePathValue = new String(zk_client.getData(nodePath, false, null));
				registerBean.register(nodePathValue, "zookeeper");
				logger.info(VALUE + "节点[" + event.getPath() + "]下的数据发生变化");
				break;
			case NodeChildrenChanged:
				logger.info(VALUE + "节点[" + event.getPath() + "]下的子节点发生变更");
				break;
			case NodeDeleted:
				logger.info(VALUE + "节点[" + event.getPath() + "]被删除");
			default:
				break;
			}
		} catch (Exception e) {
			throw new ResourceException("zookeeper配置中心发生错误[" + e.toString() + "]");
		}
	}
}