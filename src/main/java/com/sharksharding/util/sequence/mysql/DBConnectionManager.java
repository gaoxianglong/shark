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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.sharksharding.exception.ConnectionException;

/**
 * 数据源链接管理
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class DBConnectionManager {
	private static String name;
	private static String password;
	private static String jdbcUrl;
	private static String driverClass;
	private static DataSource dataSource;

	private DBConnectionManager() {
	}

	/**
	 * 获取数据库链接
	 * 
	 * @author gaoxianglong
	 * 
	 * @throws ConnectionException
	 * 
	 * @return Connection 数据库会话链接
	 */
	protected static Connection getConn() throws ConnectionException {
		Connection conn = null;
		try {
			if (null != dataSource) {
				conn = dataSource.getConnection();
			} else {
				Class.forName(getDriverClass());
				conn = DriverManager.getConnection(getJdbcUrl(), getName(), getPassword());
			}
		} catch (Exception e) {
			throw new ConnectionException(e.toString());
		}
		return conn;
	}

	/**
	 * 回收资源
	 * 
	 * @author gaoxianglong
	 * @throws SQLException
	 */
	protected static void close(Connection conn) throws SQLException {
		if (null != conn)
			conn.close();
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		DBConnectionManager.name = name;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		DBConnectionManager.password = password;
	}

	public static String getJdbcUrl() {
		return jdbcUrl;
	}

	public static void setJdbcUrl(String jdbcUrl) {
		DBConnectionManager.jdbcUrl = jdbcUrl;
	}

	public static String getDriverClass() {
		return driverClass;
	}

	public static void setDriverClass(String driverClass) {
		DBConnectionManager.driverClass = driverClass;
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		DBConnectionManager.dataSource = dataSource;
	}
}