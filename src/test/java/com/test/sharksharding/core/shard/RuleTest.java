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

import org.junit.Test;

import com.sharksharding.core.shard.RuleImpl;

import junit.framework.Assert;

/**
 * 路由规则测试类
 * 
 * @author gaoxianglong
 */
public class RuleTest {
	public @Test void testGetDbIndex() {
		Assert.assertEquals(10000 % 1024 / 32, RuleImpl.getDbIndex(10000, "#10000#%1024/32"));
	}

	public @Test void testTbResolver() {
		Assert.assertEquals(10000 % 1024 % 32, RuleImpl.getTabIndex(10000, "#10000#%1024%32"));
	}

	public @Test void testDbResolver_oneTb() {
		Assert.assertEquals(10000 % 32, RuleImpl.getDbIndexbyOne(10000, "#10000#%32"));
	}
}