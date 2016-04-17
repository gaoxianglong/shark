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
package com.sharksharding.util.sequence.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.sharksharding.exception.ResourceException;

/**
 * znode操作
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public class ZnodeService {
	public static int zk_version;

	/**
	 * 获取指定类别的已申请占位数量
	 *
	 * @author gaoxianglong
	 * 
	 * @param zk_client
	 *            session会话
	 * 
	 * @param rootNodePath
	 *            zk根节点
	 * 
	 * @param childrenNodePath
	 *            zk子节点
	 * 
	 * @param memData
	 *            内存占位数
	 * 
	 * @throws Exception
	 * 
	 * @return long 已申请占位数量
	 */
	public long getUseData(ZooKeeper zk_client, String rootNodePath, String childrenNodePath, long memData)
			throws Exception {
		final String path = rootNodePath + "/" + childrenNodePath;
		/* 检查rootNode、node节点是否存在 */
		isRootNode(zk_client, rootNodePath);
		isChildrenNode(zk_client, path, memData);
		return Integer.parseInt(String.valueOf(zk_client.getData(path, false, null)));
	}

	/**
	 * 获取当前最大占位数
	 *
	 * @author gaoxianglong
	 * 
	 * @param zk_client
	 *            session会话
	 * 
	 * @param rootNodePath
	 *            zk根节点
	 * 
	 * @throws Exception
	 * 
	 * @return long 最大占位数量
	 */
	public long maxUseData(ZooKeeper zk_client, String rootNodePath) throws Exception {
		long maxUseData = -1L;
		/* 检查rootNode节点是否存在 */
		isRootNode(zk_client, rootNodePath);
		List<String> childrenNodePaths = zk_client.getChildren(rootNodePath, false);
		if (!childrenNodePaths.isEmpty()) {
			final int size = childrenNodePaths.size();
			int[] datas = new int[size];
			for (int i = 0; i < size; i++) {
				final String path = rootNodePath + "/" + childrenNodePaths.get(i);
				datas[i] = Integer.parseInt(String.valueOf(new String(zk_client.getData(path, false, null))));
			}
			maxUseData = datas[0];
			for (int data : datas) {
				if (maxUseData < data) {
					maxUseData = data;
				}
			}
		}
		return maxUseData;
	}

	/**
	 * 根据指定的类别更新内存占位数
	 * 
	 * @author gaoxianglong
	 * 
	 * @param zk_client
	 *            session会话
	 * 
	 * @param rootNodePath
	 *            zk根节点
	 * 
	 * @param childrenNodePath
	 *            zk子节点
	 * 
	 * @param useData
	 *            当前占位数
	 * 
	 * @param memData
	 *            内存占位数
	 * 
	 * @param version
	 *            zk数据版本号
	 * 
	 * @throws Exception
	 * 
	 * @return void
	 */
	public void changeUseData(ZooKeeper zk_client, String rootNodePath, String childrenNodePath, long useData,
			long memData, int version) throws Exception {
		final String path = rootNodePath + "/" + childrenNodePath;
		/* 检查rootNode、childrenNode节点是否存在 */
		isRootNode(zk_client, rootNodePath);
		isChildrenNode(zk_client, path, memData);
		try {
			ZnodeService.zk_version = zk_client.setData(path, String.valueOf(useData).getBytes(), version).getVersion();
		} catch (Exception e) {
			/* 如果数据版本号错误,则获取最新的版本号和当前占位数 */
			useData = Long.parseLong(new String(zk_client.getData(path, false, null)));
			version = zk_client.setData(path, String.valueOf(useData).getBytes(), -1).getVersion();
			changeUseData(zk_client, rootNodePath, childrenNodePath, useData + memData, memData, version);
		}
	}

	/**
	 * 检查rootNode节点是否存在
	 * 
	 * @author gaoxianglong
	 * 
	 * @param zk_client
	 *            session会话
	 * 
	 * @param rootNodePath
	 *            zk根节点
	 * 
	 * @return void
	 */
	private void isRootNode(ZooKeeper zk_client, String rootNodePath) throws Exception {
		if (null == zk_client.exists(rootNodePath, false)) {
			zk_client.create(rootNodePath, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}

	/**
	 * 检查childrenNode节点是否存在
	 * 
	 * @author gaoxianglong
	 * 
	 * @param zk_client
	 *            session会话
	 * 
	 * @param childrenNodePath
	 *            zk子节点
	 * 
	 * @param memData
	 *            内存占位数
	 * 
	 * @return void
	 */
	private void isChildrenNode(ZooKeeper zk_client, String childrenNodePath, long memData) throws Exception {
		if (null == zk_client.exists(childrenNodePath, false)) {
			zk_client.create(childrenNodePath, String.valueOf(memData).getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
		}
	}
}