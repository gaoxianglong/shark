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

import org.apache.zookeeper.ZooKeeper;

import com.sharksharding.exception.ResourceException;
import com.sharksharding.exception.SequenceIdException;
import com.sharksharding.util.sequence.CreateSequenceIdServiceImpl;

/**
 * 生成SequenceId
 * 
 * @author JohnGao
 * 
 * @version 1.3.7
 */
public class CreateZookeeperSequenceId extends CreateSequenceIdServiceImpl {
	private StringBuffer str;

	public CreateZookeeperSequenceId() {
		str = new StringBuffer();
	}

	@Override
	public long getSequenceId(String rootPath, int idcNum, int type) throws ResourceException {
		long sequenceId = -1;
		/* 避免在并发环境下出现线程安全，则添加排他锁 */
		synchronized (this) {
			/* IDC机房编码不能够超过3位,type不能够超过6位 */
			if (idcNum < 1000 && type < 1000000) {
				ZooKeeper zk_client = ZookeeperConnectionManager.getZk_client();
				if (null != zk_client) {
					/* 清空历史数据 */
					if (null != str)
						str.delete(0, str.length());
					final String nodePath = "/" + String.valueOf(idcNum) + String.valueOf(type);
					int version = GetDataVersion.getVersion(rootPath, zk_client, nodePath);
					if (-1 != version) {
						/* 如果数据长度不足10位,高位补0 */
						for (int i = 0; i < (10 - String.valueOf(version).length()); i++)
							str.append("0");
						str.append(String.valueOf(version));
						str.insert(0, nodePath);
						sequenceId = Long.parseLong(str.toString().substring(1));
					}
				}
			} else {
				throw new SequenceIdException("idc length can not exceed 3,type length can not exceed 6");
			}
		}
		return sequenceId;
	}
}