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
package com.gxl.shark.core.shard;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.gxl.shark.util.ResolveTableName;

/**
 * 设置片名,比如通用的片名为tab,那么设置后则为tab_0000
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
@Component
public class SetTabName {
	@Resource
	private SharkJdbcTemplate jdbcTemplate;

	/**
	 * 库内分片模式下设定真正的数据库表名
	 * 
	 * @author gaoxianglong
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
	 * @param dbSize
	 *            配置文件中数据库表的数量
	 * 
	 * @param tabName
	 *            数据库通用表名
	 * 
	 * @param sql
	 * 
	 * @return String 持有真正的数据库表名的SQL
	 */
	public String setName(int dbIndex, int tabIndex, int dbSize, int tbSize, String tabName, String sql) {
		int tabIndexInDb = -1;
		if (jdbcTemplate.getConsistent()) {
			/* 计算平均每个数据库的表的数量 */
			int tbSizeInDb = tbSize / dbSize;
			/* 计算数据库表在指定数据库的索引,其算法为(库索引 * 平均每个数据库的表的数量 + 表索引) */
			tabIndexInDb = dbIndex * tbSizeInDb + tabIndex;
		} else {
			tabIndexInDb = tabIndex;
		}
		final String NEW_TABNAME = ResolveTableName.getNewTabName(tabIndexInDb, tabName, jdbcTemplate.getTbSuffix());
		return sql.replaceFirst(tabName, NEW_TABNAME);
	}

	/**
	 * 一库一表模式下设定真正的数据库表名
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbIndex
	 *            数据源索引
	 * 
	 * @param tabName
	 *            数据库通用表名
	 * 
	 * @param sql
	 * 
	 * @return String 持有真正的数据库表名的SQL
	 */
	public String setName(int dbIndex, String tabName, String sql) {
		final String NEW_TABNAME = ResolveTableName.getNewTabName(dbIndex, tabName, jdbcTemplate.getTbSuffix());
		return sql.replaceFirst(tabName, NEW_TABNAME);
	}
}