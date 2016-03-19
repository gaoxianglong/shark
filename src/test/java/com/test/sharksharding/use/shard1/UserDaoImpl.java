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
package com.test.sharksharding.use.shard1;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sharksharding.core.shard.SharkJdbcTemplate;
import com.sharksharding.sql.PropertyPlaceholderConfigurer;

/**
 * 用户信息Dao接口实现
 * 
 * @author gaoxianglong
 */
@Repository
public class UserDaoImpl implements UserDao {
	@Resource
	private SharkJdbcTemplate jdbcTemplate;

	@Resource
	private UserMapper userMapper;

	@Resource
	private PropertyPlaceholderConfigurer property;

	@Override
	public void insertUser(User user) throws Exception {
		/* 使用PropertyPlaceholderConfigurer读取配置文件中的sql信息 */
		final String SQL = property.getSql("insertUser", user.getUserinfo_Id());
		jdbcTemplate.update(SQL, new Object[] { user.getUsername() });
	}

	@Override
	public List<User> queryUserbyId(long routeKey) throws Exception {
		/* 耦合sql在业务逻辑代码中 */
		final String SQL = "select username from userinfo_test where userinfo_test_id = " + routeKey + "";
		return jdbcTemplate.query(SQL, userMapper);
	}
}