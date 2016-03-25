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
 * See the License for the specific language governing permissions AND
 * limitations under the License.
 */
package com.test.sharksharding.core.shard;

import java.util.List;

import org.junit.Test;

import com.sharksharding.core.shard.ResolveShardkey;
import com.sharksharding.core.shard.SharkInfo;

import junit.framework.Assert;

/**
 * 获取分库分表的关键字测试类
 * 
 * @author gaoxianglong
 */
public class ResolveShardkeyTest {
	/**
	 * 测试解析并获取配置文件中库内分片模式下的分库分表关键字
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testGetNamebyMany() {
		SharkInfo sharkInfo = SharkInfo.getShardInfo();
		sharkInfo.setDbRuleArray("#uid|email_hash#%1024/32");
		sharkInfo.setTbRuleArray("#uid|email_hash#%1024%32");
		List<String> keyNames = ResolveShardkey.getKeys(sharkInfo);
		Assert.assertEquals(2, keyNames.size());
		Assert.assertEquals("uid", keyNames.get(0));
		Assert.assertEquals("email_hash", keyNames.get(1));
		/* 只有一个分库分表关键字 */
		sharkInfo.setDbRuleArray("#uid#%1024/32");
		keyNames = ResolveShardkey.getKeys(sharkInfo);
		Assert.assertEquals(1, keyNames.size());
		Assert.assertEquals("uid", keyNames.get(0));
	}

	/**
	 * 测试解析并获取配置文件中一库一片模式下的分库分表关键字
	 * 
	 * @author gaoxianglong
	 */
	public @Test void testGetNamebyOne() {
		SharkInfo sharkInfo = SharkInfo.getShardInfo();
		sharkInfo.setDbRuleArray("#uid|email_hash#%32");
		List<String> keyNames = ResolveShardkey.getKeys(sharkInfo);
		Assert.assertEquals(2, keyNames.size());
		Assert.assertEquals("uid", keyNames.get(0));
		Assert.assertEquals("email_hash", keyNames.get(1));
		/* 只有一个分库分表关键字 */
		sharkInfo.setDbRuleArray("#uid#%32");
		keyNames = ResolveShardkey.getKeys(sharkInfo);
		Assert.assertEquals(1, keyNames.size());
		Assert.assertEquals("uid", keyNames.get(0));
	}
}