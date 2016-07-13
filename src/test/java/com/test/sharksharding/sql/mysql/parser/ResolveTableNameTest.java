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
package com.test.sharksharding.sql.mysql.parser;

import org.junit.Test;

import com.sharksharding.core.shard.ResolveTbName;

import junit.framework.Assert;

public class ResolveTableNameTest {
	public @Test void getTabNamebySelect() {
		String sql = "SELECT * FROM userinfo_test WHERE uid = 10000 AND name = gaoxianglong";
		Assert.assertEquals("userinfo_test", ResolveTbName.getTbName(sql));
		sql = "SELECT * FROM userinfo_test u WHERE u.uid = 10000 AND u.name = gaoxianglong";
		Assert.assertEquals("userinfo_test", ResolveTbName.getTbName(sql));
	}

	public @Test void getTabNamebyInsert() {
		String sql = "INSERT INTO userinfo_test(uid,name) VALUES(10000,gaoxianglong)";
		Assert.assertEquals("userinfo_test", ResolveTbName.getTbName(sql));
	}

	public @Test void getTabNamebyUpdate() {
		String sql = "UPDATE userinfo_test SET sex = ? WHERE uid=10000 AND name=gaoxianglong";
		Assert.assertEquals("userinfo_test", ResolveTbName.getTbName(sql));
		sql = "UPDATE userinfo_test u SET u.sex = ? WHERE u.uid=10000 AND u.name=gaoxianglong";
		Assert.assertEquals("userinfo_test", ResolveTbName.getTbName(sql));
	}

	public @Test void getTabNamebyDelete() {
		String sql = "DELETE FROM userinfo_test WHERE uid=10000 AND name=gaoxianglong";
		Assert.assertEquals("userinfo_test", ResolveTbName.getTbName(sql));
		sql = "DELETE FROM userinfo_test u WHERE u.uid=10000 AND u.name=gaoxianglong";
		Assert.assertEquals("userinfo_test", ResolveTbName.getTbName(sql));
	}
}