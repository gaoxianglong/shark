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
package com.sharksharding.util.sequence.mysql;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import com.sharksharding.exception.SequenceIdException;
import com.sharksharding.util.sequence.CreateSequenceIdServiceImpl;

/**
 * 生成基于Mysql的SequenceId
 * 
 * @author JohnGao
 * 
 * @version 1.3.5
 */
public class CreateMysqlSequenceId extends CreateSequenceIdServiceImpl {
	private CreateSequenceIdDao createUserNameDao;
	private StringBuffer str;
	private ConcurrentHashMap<Integer, Long> useDataMap;
	private ConcurrentHashMap<Integer, Integer> surplusDataMap;
	private long memData;

	public CreateMysqlSequenceId() {
		str = new StringBuffer();
		useDataMap = new ConcurrentHashMap<Integer, Long>();
		surplusDataMap = new ConcurrentHashMap<Integer, Integer>();
		createUserNameDao = new CreateSequenceIdDaoImpl();
	}

	@Override
	public long getSequenceId(int idcNum, int type, long memData) {
		long sequenceId = -1;
		/* 避免在并发环境下出现线程安全，则添加同步对象锁 */
		synchronized (this) {
			if (null != createUserNameDao) {
				this.memData = memData;
				/* idc机房编码数据长度必须为3位,type数据长度必须为2位 */
				if (3 == String.valueOf(idcNum).length() && 2 == String.valueOf(type).length()) {
					try {
						/* 根据指定的类别从内存中获取剩余的占位数量 */
						Integer surplusData = surplusDataMap.get(type);
						if (null != surplusData) {
							if (0 < surplusData) {
								surplusDataMap.put(type, --surplusData);
								Long useData = useDataMap.get(type);
								if (null == useData) {
									useData = createUserNameDao.queryUseDatabyType(type);
									useDataMap.put(type, useData);
									transactionManager();
								}
								/* 根据指定规则创建唯一的SequenceId */
								sequenceId = createSequenceId(useData - surplusData, idcNum, type);
							} else {
								/* 生成当前占位数据 */
								Long useData = createUseData();
								surplusData = (int) memData;
								/* 根据指定的类别更新当前占位数量 */
								createUserNameDao.changeUseData(type, useData);
								surplusDataMap.put(type, --surplusData);
								useDataMap.put(type, useData);
								transactionManager();
								sequenceId = createSequenceId(useData - surplusData, idcNum, type);
							}
						} else {
							Long useData = createUseData();
							surplusData = (int) memData;
							if (null != createUserNameDao.queryUseDatabyType(type)) {
								createUserNameDao.changeUseData(type, useData);
							} else {
								createUserNameDao.insertSequenceId(type, useData);
							}
							surplusDataMap.put(type, --surplusData);
							useDataMap.put(type, useData);
							transactionManager();
							sequenceId = createSequenceId(useData - surplusData, idcNum, type);
						}
					} catch (Exception e) {
						try {
							if (null != CreateSequenceIdDaoImpl.conn && !CreateSequenceIdDaoImpl.conn.isClosed()) {
								CreateSequenceIdDaoImpl.conn.rollback();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						throw new SequenceIdException("create sequenceid fail" + e.getMessage());
					} finally {
						try {
							if (null != CreateSequenceIdDaoImpl.conn && !CreateSequenceIdDaoImpl.conn.isClosed()) {
								DBConnectionManager.close(CreateSequenceIdDaoImpl.conn);
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				} else {
					throw new SequenceIdException("idc length must be equal to 3,type length must be equal to 2");
				}
			}
		}
		return sequenceId;
	}

	/**
	 * 进行事物传播特性控制的事物管理
	 * 
	 * @author gaoxianglong
	 * 
	 * @throws SQLException
	 * 
	 * @return void
	 */
	private void transactionManager() throws SQLException {
		CreateSequenceIdDaoImpl.conn.commit();
	}

	/**
	 * 生成当前占位数据
	 * 
	 * @author gaoxianglong
	 * 
	 * @throws Exception
	 * 
	 * @return long 返回生成的当前占位数据
	 */
	private long createUseData() throws Exception {
		/* 获取当前最大的占位数据 */
		Long useData = createUserNameDao.queryMaxUseData();
		return null != useData ? useData += memData : memData;
	}

	/**
	 * 根据指定规则创建唯一的userName
	 * 
	 * @author gaoxianglong
	 * 
	 * @param id
	 *            唯一序列
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房,3位数字长度
	 * 
	 * @param type
	 *            业务类别,2位数字长度
	 * 
	 * @return long 返回生成的19位数字长度的sequenceId
	 */
	private long createSequenceId(Long id, int idcNum, int type) {
		if (null != str)
			str.delete(0, str.length());
		final int length = 14 - String.valueOf(id).length();
		/* 唯一序列高位补0 */
		for (int i = 0; i < length; i++)
			str.append(0);
		str.append(id);
		str.insert(0, idcNum);
		str.insert(3, type);
		return Long.parseLong(str.toString());
	}
}