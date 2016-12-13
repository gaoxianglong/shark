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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * shark动态数据源实现
 * 
 * 该数据源继承自Spring提供的AbstractRoutingDataSource,可以根据配置文件中的数据源索引对多数据源进行动态切换,
 * 能够非常方便的实现数据源路由工作
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class SharkDatasourceGroup extends AbstractRoutingDataSource implements DataSource {
	private DataSourceHolder dataSourceHolder;
	private static Logger logger = LoggerFactory.getLogger(SharkDatasourceGroup.class);

	private SharkDatasourceGroup() {
		dataSourceHolder = SharkDataSourceHolder.getDataSourceHolder();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		int index = -1;
		if (null != dataSourceHolder) {
			/* 获取存放在ThreadLocal中的数据源索引 */
			index = dataSourceHolder.getIndex();
			logger.debug("datasource index-->" + index);
		}
		return index;
	}
}
