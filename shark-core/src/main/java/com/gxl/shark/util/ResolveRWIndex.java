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
package com.gxl.shark.util;

import com.gxl.shark.exception.IndexException;

/**
 * 读写分离起始索引解析
 * 
 * @author gaoxianglong
 */
public abstract class ResolveRWIndex {
	/**
	 * 获取master/slave的数据源启始索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param wr_index
	 *            读写权重比例，如比r0w1024
	 * 
	 * @param operation
	 *            true为master启始索引，false为slave启始索引
	 * 
	 * @throws IndexException
	 * 
	 * @return int 启始索引
	 */
	public static int getIndex(String wr_index, boolean operation) {
		int index = -1;
		if (wr_index.matches("[rR][0-9]+[wW][0-9]+")) {
			wr_index = wr_index.toUpperCase();
			String str = null;
			if (operation) {
				str = "W";
				index = Integer.parseInt(wr_index.substring(wr_index.indexOf(str) + 1));
			} else {
				str = "R";
				index = Integer.parseInt(wr_index.substring(wr_index.indexOf(str) + 1, wr_index.indexOf("W")));
			}
		} else {
			throw new IndexException("master/slave读写分离起始索引配置信息有误");
		}
		return index;
	}
}