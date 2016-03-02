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

import com.gxl.shark.exception.ShardException;

/**
 * 路由模式接口
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public interface Route {
	/**
	 * 一库一片模式
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sql
	 *            需要进行重写的sql语句
	 * 
	 * @param params
	 *            委托对象的方法入参
	 * 
	 * @param indexType
	 *            true为master启始索引，false为slave启始索引
	 * 
	 * @exception ShardException
	 * 
	 * @return Object[] 重写后的委托对象的上下文信息
	 */
	public Object[] dbRouteByOne(String sql, Object[] params, boolean indexType);

	/**
	 * 库内分片模式
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sql
	 *            需要进行重写的sql语句
	 * 
	 * @param params
	 *            委托对象的方法入参
	 * 
	 * @param indexType
	 *            true为master启始索引，false为slave启始索引
	 * 
	 * @exception ShardException
	 * 
	 * @return Object[] 重写后的委托对象的上下文信息
	 */
	public Object[] dbRouteByMany(String sql, Object[] params, boolean indexType);
}