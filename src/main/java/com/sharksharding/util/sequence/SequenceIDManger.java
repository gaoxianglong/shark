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
package com.sharksharding.util.sequence;

import javax.sql.DataSource;

import com.sharksharding.factory.CreateMysqlSequenceIdFactory;
import com.sharksharding.factory.CreateSequenceIdServiceFactory;
import com.sharksharding.factory.CreateZookeeperSequenceIdFactory;
import com.sharksharding.util.sequence.mysql.DBConnectionManager;
import com.sharksharding.util.sequence.zookeeper.ZookeeperConnectionManager;

/**
 * sequenceID的Manager类
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class SequenceIDManger {
	private static CreateSequenceIdServiceFactory createMysqlSequenceIdFactory, createZookeeperSequenceIdFactory;

	static {
		createMysqlSequenceIdFactory = new CreateMysqlSequenceIdFactory();
		createZookeeperSequenceIdFactory = new CreateZookeeperSequenceIdFactory();
	}

	/**
	 * 初始化mysql数据源信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @param name
	 *            数据库用户帐号
	 * 
	 * @param password
	 *            数据库用户密码
	 * 
	 * @param jdbcUrl
	 *            数据源地址
	 * 
	 * @param driverClass
	 *            数据库驱动
	 * 
	 * @return void
	 */
	@Deprecated
	public static void init(String name, String password, String jdbcUrl, String driverClass) {
		DBConnectionManager.setName(name);
		DBConnectionManager.setPassword(password);
		DBConnectionManager.setJdbcUrl(jdbcUrl);
		DBConnectionManager.setDriverClass(driverClass);
	}

	/**
	 * 初始化mysql数据源信息,基于连接池
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dataSource
	 *            数据源信息
	 * 
	 * @return void
	 */
	public static void init(DataSource dataSource) {
		DBConnectionManager.setDataSource(dataSource);
	}

	/**
	 * 初始化zookeeper数据源信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @param zk_address
	 *            zookeeper地址
	 * 
	 * @param zk_session_timeout
	 *            会话超时时间
	 * 
	 * @return void
	 */
	public static void init(String zk_address, int zk_session_timeout) {
		ZookeeperConnectionManager.init(zk_address, zk_session_timeout);
	}

	private SequenceIDManger() {
	}

	/**
	 * 获取mysql生成的sequenceID,其最大值不可超过二进制位数64位long类型的最大值9223372036854775807
	 * 
	 * @author gaoxianglong
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房,3位数字长度
	 * 
	 * @param type
	 *            业务类别,2位数字长度
	 * 
	 * @param memData
	 *            内存占位数量
	 * 
	 * @return long 返回生成的19位数字长度的sequenceId
	 */
	public static long getSequenceId(int idcNum, int type, long memData) {
		return createMysqlSequenceIdFactory.getCreateSequenceIdService().getSequenceId(idcNum, type, memData);
	}

	/**
	 * 获取zookeeper生成的sequenceID,其最大值不可超过二进制位数64位long类型的最大值9223372036854775807
	 * 
	 * @author gaoxianglong
	 * 
	 * @param rootPath
	 *            znode根目录
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房,3位数字长度
	 * 
	 * @param type
	 *            业务类别,6位数字长度
	 * 
	 * @param memData
	 *            内存占位数量
	 * 
	 * @return long 返回生成的19位数字长度的sequenceId
	 */
	public static long getSequenceId(String rootPath, int idcNum, int type, long memData) {
		return createZookeeperSequenceIdFactory.getCreateSequenceIdService().getSequenceId(rootPath, idcNum, type,
				memData);
	}
}