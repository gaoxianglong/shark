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

import org.apache.zookeeper.ZooKeeper;

import com.gxl.shark.resources.zookeeper.DataSourceBean;

/**
 * 注册与sharding、数据源相关的节点信息
 * 
 * @author gaoxianglong
 */
public interface RegisterNode {
	/**
	 * 注册Znode
	 * 
	 * @author gaoxianglong
	 * 
	 * @param zk_client
	 *            zookeeper客户端会话连接
	 * 
	 * @exception Exception
	 * 
	 * @return void
	 */
	public void register(ZooKeeper zk_client, DataSourceBean dataSourceBean);
}
