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

import com.sharksharding.core.config.DataSourceHolder;

/**
 * 设置数据源
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class SetDatasource {
	/**
	 * 切换数据源路由索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param index
	 *            数据库索引
	 * 
	 * @param dataSourceHolder
	 *            数据源路由选择器接口
	 * 
	 * @return void
	 */
	protected static void setIndex(int index, DataSourceHolder dataSourceHolder) {
		dataSourceHolder.setIndex(index);
	}
}