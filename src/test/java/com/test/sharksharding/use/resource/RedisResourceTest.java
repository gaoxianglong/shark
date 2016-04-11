package com.test.sharksharding.use.resource;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sharksharding.core.shard.GetJdbcTemplate;
import com.sharksharding.core.shard.SharkJdbcTemplate;
import com.sharksharding.sql.SQLTemplate;

/**
 * 从配置中心动态获取加载配置信息测试
 * 
 * @author gaoxianglong
 */
@RunWith(SpringJUnit4ClassRunner.class)
/* 基于redis的配置中心 */
@ContextConfiguration(locations = "classpath*:shark7-context.xml")
public class RedisResourceTest {
	@Resource
	private SQLTemplate sqlTemplate;

	public @Test void testInsert() {
		while (true) {
			System.out.println("input-->");
			Scanner scan = new Scanner(System.in);
			final String uid = scan.nextLine();
			SharkJdbcTemplate jdbcTemlate = GetJdbcTemplate.getSharkJdbcTemplate();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", Long.valueOf(uid));
			params.put("userName", "gaoxianglong");
			final String sql = sqlTemplate.getSql("setUserInfo", params);
			jdbcTemlate.update(sql);
		}
	}
}