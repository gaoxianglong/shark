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
package com.sharksharding.util.web.http;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.alibaba.fastjson.JSONObject;
import com.sharksharding.core.shard.SharkJdbcTemplate;

/**
 * 获取index首页数据
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public abstract class GetIndexData {
	private static String startTime;

	static {
		startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 返回首页数据的json字符串
	 * 
	 * @author gaoxianglong
	 * 
	 * @param jdbcTemplate
	 *            jdbc模板
	 * 
	 * @return String 首页数据
	 */
	protected static String getData(SharkJdbcTemplate jdbcTemplate) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("sharkVersion", "1.3.8");
		jsonObj.put("osName", System.getProperty("os.name"));
		jsonObj.put("javaVersion", System.getProperty("java.version"));
		jsonObj.put("jvmName", System.getProperty("java.vm.name"));
		jsonObj.put("javaPath", System.getProperty("java.home"));
		String shardType = null;
		if (jdbcTemplate.getIsShard()) {
			if (jdbcTemplate.getShardMode()) {
				shardType = jdbcTemplate.getConsistent() ? "片名连续的一库一片模式" : "非片名连续的一库一片模式";
			} else {
				shardType = jdbcTemplate.getConsistent() ? "片名连续的库内分片模式" : "非片名连续的库内分片模式";
			}
			jsonObj.put("shardType", shardType);
			String dbKeyName = jdbcTemplate.getDbRuleArray().split("\\#")[1];
			jsonObj.put("route", dbKeyName);
		} else {
			jsonObj.put("shardType", "未开启分库分表开关");
			jsonObj.put("route", "");
		}
		jsonObj.put("startTime", startTime);
		return jsonObj.toString();
	}
}
