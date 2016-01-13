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
package com.gxl.shark.util.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 生成唯一用户生成SequenceId的Dao接口实现
 * 
 * @author JohnGao
 */
public final class CreateSequenceIdDaoImpl implements CreateSequenceIdDao {
	public static Connection conn;
	private PreparedStatement pre;

	@Override
	public void insertSequenceId(int type, Long useData) throws SQLException {
		if (null == conn || conn.isClosed()) {
			conn = DbConnectionManager.getConn();
			conn.setAutoCommit(false);
		}
		final String SQL = "INSERT INTO shark_sequenceid(s_type, s_useData) VALUES(?, ?)";
		pre = conn.prepareStatement(SQL);
		pre.setInt(1, type);
		pre.setLong(2, useData);
		pre.execute();
		if (null != pre)
			pre.close();
	}

	@Override
	public Long queryMaxUseData() throws SQLException {
		if (null == conn || conn.isClosed()) {
			conn = DbConnectionManager.getConn();
			conn.setAutoCommit(false);
		}
		Long useData = null;
		final String SQL = "SELECT MAX(s.s_useData) FROM shark_sequenceid s FOR UPDATE";
		pre = conn.prepareStatement(SQL);
		ResultSet resultSet = pre.executeQuery();
		while (resultSet.next())
			useData = resultSet.getLong(1);
		if (null != pre)
			pre.close();
		return useData;
	}

	@Override
	public Long queryUseDatabyType(int type) throws SQLException {
		if (null == conn || conn.isClosed()) {
			conn = DbConnectionManager.getConn();
			conn.setAutoCommit(false);
		}
		Long useData = null;
		final String SQL = "SELECT s.s_useData FROM shark_sequenceid s WHERE " + "s.s_type = ? FOR UPDATE";
		pre = conn.prepareStatement(SQL);
		pre.setInt(1, type);
		ResultSet resultSet = pre.executeQuery();
		while (resultSet.next())
			useData = resultSet.getLong(1);
		if (null != pre)
			pre.close();
		return useData;
	}

	@Override
	public void changeUseData(int type, Long useData) throws SQLException {
		if (null == conn || conn.isClosed()) {
			conn = DbConnectionManager.getConn();
			conn.setAutoCommit(false);
		}
		final String SQL = "UPDATE shark_sequenceid s SET s.s_useData = ? WHERE s.s_type = ?";
		pre = conn.prepareStatement(SQL);
		pre.setLong(1, useData);
		pre.setInt(2, type);
		pre.execute();
		if (null != pre)
			pre.close();
	}
}