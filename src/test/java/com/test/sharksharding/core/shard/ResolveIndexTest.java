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

import org.junit.Test;
import com.sharksharding.core.shard.ResolveIndex;
import junit.framework.Assert;

public class ResolveIndexTest {
	public @Test void testGetIndex() {
		Assert.assertEquals(0, ResolveIndex.getBeginIndex("r1024w0", true));
		Assert.assertEquals(1024, ResolveIndex.getBeginIndex("r1024w0", false));
		Assert.assertEquals(0, ResolveIndex.getBeginIndex("R1024W0", true));
		Assert.assertEquals(1024, ResolveIndex.getBeginIndex("R0w1024", true));
	}
}