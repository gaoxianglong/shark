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
package com.gxl.shark.util.sequence.mysql;

import java.sql.SQLException;

/**
 * 生成唯一用户生成SequenceId的Dao接口
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public interface CreateSequenceIdDao {
	/**
	 * 根据指定的用户类别获取已申请的占位数量
	 * 
	 * @author gaoxianglong
	 * 
	 * @param type
	 *            类别
	 * 
	 * @throws SQLException
	 * 
	 * @return Long 返回当前占位数量
	 */
	public Long queryUseDatabyType(int type) throws SQLException;

	/**
	 * 根据指定的用户类别创建占位数量
	 * 
	 * @author gaoxianglong
	 * 
	 * @param type
	 *            类别
	 * 
	 * @param useData
	 *            申请占位数量
	 * 
	 * @throws SQLException
	 * 
	 * @return void
	 */
	public void insertSequenceId(int type, Long useData) throws SQLException;

	/**
	 * 获取当前最大的占位数量
	 * 
	 * @author gaoxianglong
	 * 
	 * @throws SQLException
	 * 
	 * @return Long 返回最大的占位边界
	 */
	public Long queryMaxUseData() throws SQLException;

	/**
	 * 根据指定的用户类别更新剩余占位数量和当前占位数量
	 * 
	 * @author gaoxianglong
	 * 
	 * @param type
	 *            类别
	 * 
	 * @param useData
	 *            申请占位数量
	 * 
	 * @throws SQLException
	 * 
	 * @return void
	 */
	public void changeUseData(int type, Long useData) throws SQLException;
}