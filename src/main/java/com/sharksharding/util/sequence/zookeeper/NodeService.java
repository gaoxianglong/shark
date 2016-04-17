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
 * @version 1.3.7
 */
public class NodeService {
	public static int version;

	/**
	 * 获取指定类别的已申请占位数量
	 *
	 * @author gaoxianglong
	 * 
	 * @param zk_client
	 *            session会话
	 * 
	 * @param rootPath
	 *            根节点
	 * 
	 * @param nodePath
	 *            子节点
	 * 
	 * @param memData
	 *            内存占位数
	 * 
	 * @throws Exception
	 * 
	 * @return long 占位数量
	 */
	public long getUseData(ZooKeeper zk_client, String rootPath, String nodePath, long memData) throws Exception {
		long useData = -1;
		if (null != zk_client && null != rootPath && null != nodePath && 0 < memData) {
			isRootNode(zk_client, rootPath);
			isNode(zk_client, rootPath, nodePath, memData);
			useData = Integer.parseInt(String.valueOf(zk_client.getData(rootPath + "/" + nodePath, false, null)));
		}
		return useData;
	}

	/**
	 * 获取当前最大占位数
	 *
	 * @author gaoxianglong
	 * 
	 * @param zk_client
	 *            session会话
	 * 
	 * @param rootPath
	 *            根节点
	 * 
	 * @throws Exception
	 * 
	 * @return long 最大占位数量
	 */
	public long maxUseData(ZooKeeper zk_client, String rootPath) throws Exception {
		long maxUseData = 0L;
		if (null != zk_client && null != rootPath) {
			isRootNode(zk_client, rootPath);
			List<String> nodePaths = zk_client.getChildren(rootPath, false);
			if (!nodePaths.isEmpty()) {
				final int size = nodePaths.size();
				int[] datas = new int[size];
				for (int i = 0; i < size; i++) {
					final String nodePath = rootPath + "/" + nodePaths.get(i);
					datas[i] = Integer.parseInt(String.valueOf(new String(zk_client.getData(nodePath, false, null))));
				}
				maxUseData = datas[0];
				for (int data : datas) {
					if (maxUseData < data) {
						maxUseData = data;
					}
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
	 * @param rootPath
	 *            根节点
	 * 
	 * @param nodePath
	 *            子节点
	 * 
	 * @param useData
	 *            当前占位数
	 * 
	 * @param memData
	 *            内存占位数
	 * 
	 * @param version
	 *            数据版本号
	 * 
	 * @throws Exception
	 * 
	 * @return int 最新版本号
	 */
	public void changeUseData(ZooKeeper zk_client, String rootPath, String nodePath, long useData, long memData,
			int version) throws Exception {
		if (null != zk_client && null != rootPath && null != nodePath && 0 < useData && 0 < memData) {
			isRootNode(zk_client, rootPath);
			isNode(zk_client, rootPath, nodePath, memData);
			final String path = rootPath + "/" + nodePath;
			try {
				Stat stat = zk_client.setData(path, String.valueOf(useData).getBytes(), version);
				NodeService.version = stat.getVersion();
			} catch (Exception e) {
				/* 如果数据版本号错误,则获取最新的版本号和当前占位数 */
				useData = Long.parseLong(new String(zk_client.getData(path, false, null)));
				Stat stat = zk_client.setData(path, String.valueOf(useData).getBytes(), -1);
				version = stat.getVersion();
				changeUseData(zk_client, rootPath, nodePath, useData + memData, memData, version);
			}
		}
	}

	/**
	 * 检查rootPath节点是否存在
	 * 
	 * @author gaoxianglong
	 * 
	 * @return void
	 */
	private void isRootNode(ZooKeeper zk_client, String rootPath) throws Exception {
		if (null == zk_client.exists(rootPath, false)) {
			zk_client.create(rootPath, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}

	/**
	 * 检查nodePath节点是否存在
	 * 
	 * @author gaoxianglong
	 * 
	 * @return void
	 */
	private void isNode(ZooKeeper zk_client, String rootPath, String nodePath, long memData) throws Exception {
		final String path = rootPath + "/" + nodePath;
		if (null == zk_client.exists(path, false)) {
			zk_client.create(path, String.valueOf(memData).getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}
}