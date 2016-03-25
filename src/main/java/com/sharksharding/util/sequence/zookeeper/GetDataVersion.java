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

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import com.sharksharding.exception.ResourceException;

/**
 * 获取node数据的唯一版本号
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class GetDataVersion {
	protected static int getVersion(String rootPath, ZooKeeper zk_client, String nodePath) throws ResourceException {
		int version = -1;
		if (null != zk_client && null != rootPath && null != nodePath) {
			final String PATH = rootPath + nodePath;
			try {
				/* 如果rootNode不存在,则创建 */
				if (null == zk_client.exists(rootPath, false))
					zk_client.create(rootPath, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				/* 如果node不存在,则创建 */
				if (null == zk_client.exists(PATH, false))
					zk_client.create(PATH, new byte[] {}, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				/* 改变某一个node的数据后,获取唯一版本号 */
				Stat stat = zk_client.setData(PATH, new byte[] {}, -1);
				version = stat.getVersion();
			} catch (Exception e) {
				throw new ResourceException("zookeeper error," + e.getMessage());
			}
		}
		return version;
	}
}