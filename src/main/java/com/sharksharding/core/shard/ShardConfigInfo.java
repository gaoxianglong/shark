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
 * shark分库分表配置信息
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.0
 */
public class ShardConfigInfo {
	private static ShardConfigInfo shardInfo;
	private String wr_index;
	private boolean isShard;
	private boolean shardMode;
	private boolean consistent;
	private String dbRuleArray;
	private String tbRuleArray;
	private String tbSuffix;

	public String getWr_index() {
		return wr_index;
	}

	public void setWr_index(String wr_index) {
		this.wr_index = wr_index;
	}

	public boolean getIsShard() {
		return isShard;
	}

	public void setIsShard(boolean isShard) {
		this.isShard = isShard;
	}

	public boolean getShardMode() {
		return shardMode;
	}

	public void setShardMode(boolean shardMode) {
		this.shardMode = shardMode;
	}

	public boolean getConsistent() {
		return consistent;
	}

	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
	}

	public String getDbRuleArray() {
		return dbRuleArray;
	}

	public void setDbRuleArray(String dbRuleArray) {
		this.dbRuleArray = dbRuleArray;
	}

	public String getTbRuleArray() {
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

	public static void setShardInfo(ShardConfigInfo shardInfo) {
		ShardConfigInfo.shardInfo = shardInfo;
	}

	static {
		shardInfo = new ShardConfigInfo();
	}

	private ShardConfigInfo() {
	}

	public static ShardConfigInfo getShardInfo() {
		return shardInfo;
	}
}