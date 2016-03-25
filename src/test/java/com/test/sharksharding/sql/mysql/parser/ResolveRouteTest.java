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

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sharksharding.core.shard.ResolveRoute;

import junit.framework.Assert;

public class ResolveRouteTest {
	public static List<String> rules;

	public @BeforeClass static void init() {
		rules = new ArrayList<String>();
		rules.add("uid");
		rules.add("email_hash");
	}

	public @Test void getRoutebySelect() {
		final String SQL = "SELECT * FROM userinfo_test WHERE uid = 100000000101 and name=?";
		Assert.assertEquals(100000000101L, ResolveRoute.getRoute(SQL, rules));
	}

	public @Test void getRoutebyInsert() {
		final String SQL = "INSERT INTO userinfo_test(uid,name) VALUES(100000000101,?)";
		Assert.assertEquals(100000000101L, ResolveRoute.getRoute(SQL, rules));
	}

	public @Test void getRoutebyUpdate() {
		final String SQL = "UPDATE userinfo_test SET sex = ? WHERE uid=100000000101 AND email=?";
		Assert.assertEquals(100000000101L, ResolveRoute.getRoute(SQL, rules));
	}

	public @Test void getRoutebyDelete() {
		final String SQL = "DELETE FROM userinfo_test WHERE uid=100000000101 AND name=?";
		Assert.assertEquals(100000000101L, ResolveRoute.getRoute(SQL, rules));
	}
}