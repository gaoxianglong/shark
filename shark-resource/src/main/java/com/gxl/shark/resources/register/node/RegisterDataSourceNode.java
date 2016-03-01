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
package com.gxl.shark.resources.register.node;

import javax.annotation.Resource;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import com.gxl.shark.exception.ResourceException;
import com.gxl.shark.resources.register.bean.RegisterBean;
import com.gxl.shark.resources.watcher.DataSourceWatcher;
import com.gxl.shark.resources.zookeeper.DataSourceBean;

/**
 * 注册与sharding、数据源相关的节点信息实现
 * 
 * @author gaoxianglong
 */
@Component
public class RegisterDataSourceNode implements RegisterNode {
	@Resource
	private DataSourceWatcher dataSourceWatcher;

	@Resource(name = "registerDataSource")
	private RegisterBean registerBean;

	@Override
	public void register(ZooKeeper zk_client, DataSourceBean dataSourceBean) {
		final String nodePath = dataSourceBean.getNodePath();
		String nodePathValeu = null;
		if (null != nodePath) {
			dataSourceWatcher.init(zk_client, dataSourceBean);
			try {
				/* 注册节点 */
				if (null != zk_client.exists(nodePath, dataSourceWatcher))
					nodePathValeu = new String(zk_client.getData(nodePath, false, null));
				/* 动态向spring的ioc容器中注册相关bean */
				registerBean.register(nodePathValeu, dataSourceBean);
			} catch (Exception e) {
				throw new ResourceException("zookeeper配置中心发生错误[" + e.toString() + "]");
			}
		}
	}
}