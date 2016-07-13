package com.test.sharksharding.use.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sharksharding.core.shard.GetJdbcTemplate;
import com.sharksharding.sql.SQLTemplate;

/**
 * 从配置中心动态获取加载配置信息测试
 *
 * @author gaoxianglong
 */
@RunWith(SpringJUnit4ClassRunner.class)
/* 基于zookeeper的配置中心 */
@ContextConfiguration(locations = "classpath*:shark4-context.xml")
public class ZkResourceTest {
	@Resource
	private SQLTemplate sqlTemplate;
	
	public @Test void testInsert() {
		while (true) {
			System.out.println("input-->");
			Scanner scan = new Scanner(System.in);
			final String uid = scan.nextLine();
			JdbcTemplate jdbcTemplate = GetJdbcTemplate.getJdbcTemplate("jdbcTemplate");
			System.out.println("hash:" + jdbcTemplate.hashCode());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", Long.valueOf(uid));
			params.put("userName", "gaoxianglong");
			final String sql = sqlTemplate.getSql("setUserInfo", params);
			jdbcTemplate.update(sql);
		}
	}
}