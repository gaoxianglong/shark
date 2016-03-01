package com.gxl.test.shark.resource;

import java.util.Scanner;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gxl.shark.core.shard.GetJdbcTemplate;
import com.gxl.shark.core.shard.SharkJdbcTemplate;

/**
 * 从配置中心动态获取加载配置信息测试
 * 
 * @author gaoxianglong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:shark6-context.xml")
public class ResourceTest {
	public @Test void testInsert() {
		while (true) {
			Scanner scan = new Scanner(System.in);
			final String uid = scan.nextLine();
			SharkJdbcTemplate jdbcTemlate = GetJdbcTemplate.getSharkJdbcTemplate();
			jdbcTemlate.update(
					"insert into userinfo_test(userinfo_test_id,userName) values(" + Long.valueOf(uid) + ",'value')");
		}
	}
}