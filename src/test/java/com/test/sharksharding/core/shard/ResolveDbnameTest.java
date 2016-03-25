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
package com.test.sharksharding.core.shard;

import org.junit.Assert;
import org.junit.Test;
import com.sharksharding.core.shard.ResolveDbname;

public class ResolveDbnameTest {
	public @Test void testGetNewDbName() {
		Assert.assertEquals("userinfo_test_0010", ResolveDbname.getNewDbName(10, "userinfo_test", "_0000"));
		Assert.assertEquals("userinfo_test_0100", ResolveDbname.getNewDbName(100, "userinfo_test", "_0000"));
		Assert.assertEquals("userinfo_test_1000", ResolveDbname.getNewDbName(1000, "userinfo_test", "_0000"));
	}
}