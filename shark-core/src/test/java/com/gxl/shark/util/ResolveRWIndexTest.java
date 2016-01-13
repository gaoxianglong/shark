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
package com.gxl.shark.util;

import org.junit.Test;

import com.gxl.shark.util.ResolveRWIndex;

import junit.framework.Assert;

public class ResolveRWIndexTest {
	public @Test void testGetIndex() {
		Assert.assertEquals(0, ResolveRWIndex.getIndex("r1024w0", true));
		Assert.assertEquals(1024, ResolveRWIndex.getIndex("r1024w0", false));
		Assert.assertEquals(0, ResolveRWIndex.getIndex("R1024W0", true));
		Assert.assertEquals(1024, ResolveRWIndex.getIndex("R0w1024", true));
	}
}