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

import com.sharksharding.util.ConfigInfoValidate;
import com.sharksharding.util.LoadSharkLogo;
import com.sharksharding.util.LoadVersion;

/**
 * shark分库分表规则
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.3
 */
public class ShardRule {
	/* master/slave读写的起始索引 */
	private String wr_index = "r0w0";
	/* 分库分表开关 */
	private boolean isShard;
	/* 分库分表模式,true为多库多表,false为单库多表 */
	private boolean shardMode;
	/* 表名是否连续,true为连续,false为不连续 */
	private boolean consistent;
	/* 分表规则 */
	private String dbRuleArray;
	/* 分库规则 */
	private String tbRuleArray;
	/* 分表后缀 */
	private String tbSuffix = "_0000";
	private ShardConfigInfo sharkInfo;
	private Logger logger = LoggerFactory.getLogger(ShardRule.class);

	public ShardRule() {
		sharkInfo = ShardConfigInfo.getShardInfo();
	}

	public void init() {
		sharkInfo.setWr_index(this.getWr_index());
		sharkInfo.setIsShard(this.isShard());
		sharkInfo.setShardMode(this.isShardMode());
		sharkInfo.setConsistent(this.isConsistent());
		sharkInfo.setDbRuleArray(this.getDbRuleArray());
		sharkInfo.setTbRuleArray(this.getTbRuleArray());
		sharkInfo.setTbSuffix(this.getTbSuffix());
		logger.debug("wr_index-->" + sharkInfo.getWr_index() + "\tisShard-->" + sharkInfo.getIsShard()
				+ "\tshardMode-->" + sharkInfo.getShardMode() + "\tconsistent" + sharkInfo.getConsistent()
				+ "\tdbRuleArray" + sharkInfo.getDbRuleArray() + "\ttbRuleArray" + sharkInfo.getTbRuleArray());
		logger.info(
				"\nWelcome to Shark\n" + LoadSharkLogo.load().replaceFirst("\\[version\\]", LoadVersion.getVersion()));
		/* 进行配置合法性验证 */
		new ConfigInfoValidate(sharkInfo).validate();
	}

	public String getWr_index() {
		return wr_index;
	}

	public void setWr_index(String wr_index) {
		this.wr_index = wr_index;
	}

	public boolean isShard() {
		return isShard;
	}

	public void setIsShard(boolean isShard) {
		this.isShard = isShard;
	}

	public boolean isShardMode() {
		return shardMode;
	}

	public void setShardMode(boolean shardMode) {
		this.shardMode = shardMode;
	}

	public boolean isConsistent() {
		return consistent;
	}

	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
	}

	public String getDbRuleArray() {
		if (null != dbRuleArray) {
			dbRuleArray = dbRuleArray.toLowerCase().replaceAll("\\s", "");
		}
		return dbRuleArray;
	}

	public void setDbRuleArray(String dbRuleArray) {
		this.dbRuleArray = dbRuleArray;
	}

	public String getTbRuleArray() {
		if (null != tbRuleArray) {
			tbRuleArray = tbRuleArray.toLowerCase().replaceAll("\\s", "");
		}
		return tbRuleArray;
	}

	public void setTbRuleArray(String tbRuleArray) {
		this.tbRuleArray = tbRuleArray;
	}

	public String getTbSuffix() {
		return tbSuffix;
	}

	public void setTbSuffix(String tbSuffix) {
		this.tbSuffix = tbSuffix;
	}
}