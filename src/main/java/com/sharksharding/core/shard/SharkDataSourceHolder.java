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
 * 数据源路由选择器
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class SharkDataSourceHolder implements DataSourceHolder {
	/*
	 * 关于并发环境下AbstractRoutingDataSource的线程安全问题,
	 * 由SharkDataSourceHolder中的ThreadLocal负责保障。每个线程的数据源索引都保存在ThreadLocal中,非线程共享。
	 * 
	 * 流程:getConnection() --> AbstractRoutingDataSource.determineTargetDataSource() --> AbstractRoutingDataSource.determineCurrentLookupKey()
	 * 
	 * 当jdbc调用getConnection()方法获取数据库会话时,
	 * 会先调用AbstractRoutingDataSource.determineTargetDataSource()方法获取出具体的数据源,
	 * 而此方法内部需要调用重写后的determineCurrentLookupKey()方法才知道线程存放在ThreadLocal中的数据源索引。
	 */
	private static final ThreadLocal<Integer> holder;

	static {
		holder = new ThreadLocal<Integer>();
	}

	@Override
	public void setIndex(int index) {
		holder.set(index);
	}

	@Override
	public int getIndex() {
		return holder.get();
	}
}