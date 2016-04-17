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

import java.util.concurrent.ConcurrentHashMap;

import org.apache.zookeeper.ZooKeeper;
import com.sharksharding.exception.ResourceException;
import com.sharksharding.exception.SequenceIdException;
import com.sharksharding.util.sequence.CreateSequenceIdServiceImpl;

/**
 * 生成SequenceId
 * 
 * @author JohnGao
 * 
 * @version 1.4.1
 */
public class CreateZookeeperSequenceId extends CreateSequenceIdServiceImpl {
	private StringBuffer str;
	private long memData;
	private ZooKeeper zk_client;
	private ConcurrentHashMap<Integer, Long> useDataMap;
	private ConcurrentHashMap<Integer, Integer> surplusDataMap;
	public static ConcurrentHashMap<String, Integer> versionMap;
	private ZnodeService znodeService;

	public CreateZookeeperSequenceId() {
		str = new StringBuffer();
		znodeService = new ZnodeService();
		useDataMap = new ConcurrentHashMap<Integer, Long>();
		surplusDataMap = new ConcurrentHashMap<Integer, Integer>();
		versionMap = new ConcurrentHashMap<String, Integer>();
	}

	@Override
	public long getSequenceId(String rootNodePath, int idcNum, int type, long memData) {
		long sequenceId = -1;
		/* 避免在并发环境下出现线程安全，则添加排他锁 */
		synchronized (this) {
			zk_client = ZookeeperConnectionManager.getZk_client();
			if (null != zk_client) {
				this.memData = memData;
				final String childrenNodePath = String.valueOf(type);
				/* idc机房编码数据长度必须为3位,type数据长度必须为6位 */
				if (3 == String.valueOf(100).length() && 6 == childrenNodePath.length()) {
					try {
						/* 根据指定的类别从内存中获取剩余的占位数量 */
						Integer surplusData = surplusDataMap.get(type);
						if (null != surplusData) {
							if (0 < surplusData) {
								surplusDataMap.put(type, --surplusData);
								Long useData = useDataMap.get(type);
								if (null == useData) {
									useData = znodeService.getUseData(zk_client, rootNodePath, childrenNodePath,
											memData);
									useDataMap.put(type, useData);
								}
								sequenceId = createSequenceId(useData - surplusData, idcNum, type);
							} else {
								/* 生成当前占位数 */
								Long useData = createUseData(zk_client, rootNodePath);
								surplusData = (int) memData;
								Integer version = versionMap.get(childrenNodePath);
								if (null == version)
									version = 1;
								/* 根据指定的类别更新当前占位数量 */
								znodeService.changeUseData(zk_client, rootNodePath, childrenNodePath, useData, memData,
										version);
								version = ZnodeService.zk_version;
								versionMap.put(childrenNodePath, version);
								surplusDataMap.put(type, --surplusData);
								useDataMap.put(type, useData);
								sequenceId = createSequenceId(useData - surplusData, idcNum, type);
							}
						} else {
							Long useData = createUseData(zk_client, rootNodePath);
							surplusData = (int) memData;
							Integer version = versionMap.get(childrenNodePath);
							if (null == version)
								version = 1;
							znodeService.changeUseData(zk_client, rootNodePath, childrenNodePath, useData, memData,
									version);
							version = ZnodeService.zk_version;
							versionMap.put(childrenNodePath, version);
							surplusDataMap.put(type, --surplusData);
							useDataMap.put(type, useData);
							sequenceId = createSequenceId(useData - surplusData, idcNum, type);
						}
					} catch (Exception e) {
						throw new ResourceException("zookeeper error," + e.getMessage());
					}
				} else {
					throw new SequenceIdException("idc length must be equal to 3,type length must be equal to 6");
				}
			}
		}
		return sequenceId;
	}

	/**
	 * 创建SequenceId
	 * 
	 * @author gaoxianglong
	 * 
	 * @param id
	 *            唯一序列
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的idc机房,3位数字长度
	 * 
	 * @param type
	 *            业务类别,6位数字长度
	 * 
	 * @return long 返回生成的19位数字长度的sequenceId
	 */
	private long createSequenceId(Long id, int idcNum, int type) {
		if (null != str)
			str.delete(0, str.length());
		final int length = 10 - String.valueOf(id).length();
		/* 唯一序列高位补0 */
		for (int i = 0; i < length; i++)
			str.append("0");
		str.append(id);
		str.insert(0, idcNum);
		str.insert(3, type);
		return Long.parseLong(str.toString());
	}

	/**
	 * 生成当前占位数据
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
	 * @return long 返回生成的当前占位数据
	 */
	private long createUseData(ZooKeeper zk_client, String rootNodePath) throws Exception {
		/* 获取当前最大的占位数据 */
		Long useData = znodeService.maxUseData(zk_client, rootNodePath);
		return null != useData ? useData += memData : memData;
	}
}