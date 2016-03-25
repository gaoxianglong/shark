/*
 * Copyright 1999-2101 Alibaba Group Holding Ltd.
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
package com.sharksharding.util;

import com.sharksharding.core.shard.SharkInfo;
import com.sharksharding.exception.ValidateException;

/**
 * 配置合法性验证
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.0
 */
public final class ConfigValidate {
	private SharkInfo sharkInfo;

	public ConfigValidate(SharkInfo sharkInfo) {
		this.sharkInfo = sharkInfo;
	}

	public void validate() {
		wr_index();
		rule();
	}

	/**
	 * 验证数据源启始索引
	 *
	 * @author gaoxianglong
	 * 
	 * @exception ValidateException
	 */
	private void wr_index() {
		final String wr_index = sharkInfo.getWr_index();
		if (!wr_index.matches("[rR][0-9]+[wW][0-9]+")) {
			throw new ValidateException("wr_index configuration error");
		}
	}

	public void rule() {
		if (sharkInfo.getIsShard()) {
			if (sharkInfo.getShardMode()) {
				validationOneTbRule();
			} else {
				validationManyTbRule();
			}
		}
	}

	/**
	 * 验证配置文件中一库一片模式的分库分表规则的配置信息是否有误
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbRuleArray
	 *            配置文件中的分库规则
	 * 
	 * @return boolean 验证结果
	 */
	private void validationOneTbRule() {
		final String dbRuleArray = sharkInfo.getDbRuleArray();
		if (!dbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+")) {
			throw new ValidateException("dbRuleArray configuration error");
		}
	}

	/**
	 * 验证配置文件中库内分片模式的分库分表规则的配置信息是否有误
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbRuleArray
	 *            配置文件中的分库规则
	 * 
	 * @param tbRuleArray
	 *            配置文件中的分表规则
	 * 
	 * @return boolean 验证结果
	 */
	private void validationManyTbRule() {
		final String dbRuleArray = sharkInfo.getDbRuleArray();
		final String tbRuleArray = sharkInfo.getTbRuleArray();
		final String dbKeyName = dbRuleArray.split("\\#")[1];
		final String tbKeyName = tbRuleArray.split("\\#")[1];
		if (!dbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+[/][0-9]+")
				|| !tbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+[%][0-9]+") || !dbKeyName.equals(tbKeyName)) {
			throw new ValidateException("dbRuleArray or tbRuleArray configuration error");
		}
	}
}