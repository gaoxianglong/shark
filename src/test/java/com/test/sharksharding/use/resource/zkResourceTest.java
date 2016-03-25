package com.test.sharksharding.use.resource;

import java.util.Scanner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.sharksharding.core.shard.GetJdbcTemplate;
import com.sharksharding.core.shard.SharkJdbcTemplate;

/**
 * 从配置中心动态获取加载配置信息测试
 *
 * @author gaoxianglong
 */
@RunWith(SpringJUnit4ClassRunner.class)
/* 基于zookeeper的配置中心 */
@ContextConfiguration(locations = "classpath*:shark6-context.xml")
public class ZkResourceTest {
	public @Test void testInsert() {
		while (true) {
			System.out.println("input-->");
			Scanner scan = new Scanner(System.in);
			final String uid = scan.nextLine();
			SharkJdbcTemplate jdbcTemlate = GetJdbcTemplate.getSharkJdbcTemplate();
			jdbcTemlate.update("insert into userinfo_test(uid,userName) values(" + Long.valueOf(uid) + ",'value')");
		}
	}
}