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
package com.sharksharding.core.shard;

/**
 * 数据路由前重设数据库表名,比如通用的表名为tab,那么重设后为tab_0000
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class SetTbName {
	/**
	 * 多库多表模式下重设真正的数据库表名
	 * 
	 * @author gaoxianglong
	 * 
	 * @param ShardConfigInfo
	 *            分库分表配置信息
	 * 
	 * @param dbIndex
	 *            数据源索引
	 * 
	 * @param tbIndex
	 *            数据库表索引
	 * 
	 * @param dbSize
	 *            配置文件中数据库的数量
	 * 
	 * @param tbSize
	 *            配置文件中数据库表的数量
	 * 
	 * @param tbName
	 *            数据库通用表名
	 * 
	 * @param sql
	 * 
	 * @return String 持有真正的数据库表名的SQL
	 */
	protected static String setName(ShardConfigInfo sharkConfigInfo, int dbIndex, int tbIndex, int dbSize, int tbSize,
			String tbName, String sql) {
		int tbIndexInDb = -1;
		if (sharkConfigInfo.getConsistent()) {
			/* 计算平均每个数据库的表的数量 */
			int tbSizeInDb = tbSize / dbSize;
			/* 计算数据库表在指定数据库的索引,其算法为(库索引 * 平均每个数据库的表的数量 + 表索引) */
			tbIndexInDb = dbIndex * tbSizeInDb + tbIndex;
		} else {
			tbIndexInDb = tbIndex;
		}
		final String NEW_TBNAME = ResolveTbName.getNewTbName(tbIndexInDb, tbName, sharkConfigInfo.getTbSuffix());
		return sql.replaceFirst(tbName, NEW_TBNAME);
	}

	/**
	 * 单库多表模式下设定真正的数据库表名
	 * 
	 * @author gaoxianglong
	 * 
	 * @param ShardConfigInfo
	 *            分库分表配置信息
	 * 
	 * @param tbIndex
	 *            数据库表索引
	 * 
	 * @param tbName
	 *            数据库通用表名
	 * 
	 * @param sql
	 * 
	 * @return String 持有真正的数据库表名的SQL
	 */
	protected static String setName(ShardConfigInfo sharkConfigInfo, int tbIndex, String tbName, String sql) {
		final String NEW_TBNAME = ResolveTbName.getNewTbName(tbIndex, tbName, sharkConfigInfo.getTbSuffix());
		return sql.replaceFirst(tbName, NEW_TBNAME);
	}
}