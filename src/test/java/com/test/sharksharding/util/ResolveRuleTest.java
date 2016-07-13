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
package com.test.sharksharding.util;

import java.util.List;

import org.junit.Test;

import com.sharksharding.core.shard.ResolveRouteRule;

import junit.framework.Assert;

public class ResolveRuleTest {
	public @Test void testResolveDbRule() {
		List<Integer> list = ResolveRouteRule.resolveDbRule("#userinfo_id|email_hash#%1024/32");
		if (!list.isEmpty()) {
			final int TB_SIZE = list.get(0);
			final int DB_SIZE = list.get(1);
			Assert.assertEquals(1024, TB_SIZE);
			Assert.assertEquals(32, DB_SIZE);
		}
	}

	public @Test void testResolveTabRule() {
		List<Integer> list = ResolveRouteRule.resolveTbRule("#userinfo_id|email_hash#%1024%32", true);
		if (!list.isEmpty()) {
			final int TB_SIZE = list.get(0);
			final int DB_SIZE = list.get(1);
			Assert.assertEquals(1024, TB_SIZE);
			Assert.assertEquals(32, DB_SIZE);
		}
	}
}