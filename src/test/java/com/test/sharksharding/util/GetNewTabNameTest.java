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

import org.junit.Test;

import com.sharksharding.core.shard.ResolveTbName;

public class GetNewTabNameTest {
	public @Test void getNewTabName() {
		System.out.println(ResolveTbName.getNewTbName(1, "userinfo_test", "_00000"));
		System.out.println(ResolveTbName.getNewTbName(1024, "userinfo_test", "_0000"));
	}
}