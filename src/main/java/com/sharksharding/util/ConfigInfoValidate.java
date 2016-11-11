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
package com.sharksharding.util;

import com.sharksharding.core.shard.ShardConfigInfo;
import com.sharksharding.exception.ValidateException;

/**
 * 配置合法性验证
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.0
 */
public final class ConfigInfoValidate {
	private ShardConfigInfo sharkInfo;

	public ConfigInfoValidate(ShardConfigInfo sharkInfo) {
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
			throw new ValidateException("wr_index config error");
		}
	}

	public void rule() {
		if (sharkInfo.getIsShard()) {
			if (sharkInfo.getShardMode()) {
				validationRouteMany();
			} else {
				validationRouteSingle();
			}
		}
	}

	/**
	 * 验证配置文件中单库多片模式的分库分表规则的配置信息是否有误
	 * 
	 * @author gaoxianglong
	 * 
	 * @return boolean 验证结果
	 */
	private void validationRouteSingle() {
		final String tbRuleArray = sharkInfo.getTbRuleArray();
		if (!tbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+")) {
			throw new ValidateException("tbRuleArray config error");
		}
	}

	/**
	 * 验证配置文件中多库多片模式的分库分表规则的配置信息是否有误
	 * 
	 * @author gaoxianglong
	 * 
	 * @return boolean 验证结果
	 */
	private void validationRouteMany() {
		final String dbRuleArray = sharkInfo.getDbRuleArray();
		final String tbRuleArray = sharkInfo.getTbRuleArray();
		final String dbKeyName = dbRuleArray.split("\\#")[1];
		final String tbKeyName = tbRuleArray.split("\\#")[1];
		if (!dbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+[/][0-9]+")
				|| !tbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+[%][0-9]+") || !dbKeyName.equals(tbKeyName)) {
			throw new ValidateException("dbRuleArray or tbRuleArray config error");
		}
	}
}