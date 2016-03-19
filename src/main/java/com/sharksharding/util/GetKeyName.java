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

import java.util.Arrays;
import java.util.List;

import com.sharksharding.exception.ShardException;

/**
 * 获取分库分表的关键字
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public abstract class GetKeyName {
	/**
	 * 解析并获取配置文件中的分库分表关键字
	 * 
	 * @author gaoxianglong
	 * 
	 * @param shardMode
	 *            分表模式,true为一库一片,false为库内分片
	 * 
	 * @param dbRuleArray
	 *            配置文件中的分库规则
	 * 
	 * @param tbRuleArray
	 *            配置文件中的分表规则
	 * 
	 * @exception ShardException
	 * 
	 * @return Set<String> 分库分表关键字
	 */
	public static List<String> getName(boolean shardMode, String dbRuleArray, String tbRuleArray) {
		List<String> keyNames = null;
		if (null == dbRuleArray)
			return keyNames;
		/* 验证分片模式 */
		if (shardMode) {
			if (validationOneTbRule(dbRuleArray)) {
				String dbKeyName = dbRuleArray.split("\\#")[1];
				keyNames = Arrays.asList(dbKeyName.split("\\|"));
			} else {
				throw new ShardException("分库分表规则配置信息有误");
			}
		} else {
			if (null == tbRuleArray)
				return keyNames;
			if (validationManyTbRule(dbRuleArray, tbRuleArray)) {
				String dbKeyName = dbRuleArray.split("\\#")[1];
				String tbKeyName = tbRuleArray.split("\\#")[1];
				if (dbKeyName.equals(tbKeyName)) {
					keyNames = Arrays.asList(dbKeyName.split("\\|"));
				} else {
					throw new ShardException("分库分表规则配置信息有误");
				}
			} else {
				throw new ShardException("分库分表规则配置信息有误");
			}
		}
		return keyNames;
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
	private static boolean validationManyTbRule(String dbRuleArray, String tbRuleArray) {
		boolean result = false;
		if (dbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+[/][0-9]+")) {
			if (tbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+[%][0-9]+")) {
				result = true;
			}
		}
		return result;
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
	private static boolean validationOneTbRule(String dbRuleArray) {
		boolean result = false;
		if (dbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+")) {
			result = true;
		}
		return result;
	}
}