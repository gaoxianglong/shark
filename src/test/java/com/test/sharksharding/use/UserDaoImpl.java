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
package com.test.sharksharding.use;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.sharksharding.sql.PropertyPlaceholderConfigurer;
import com.sharksharding.sql.SQLTemplate;

/**
 * 用户信息Dao接口实现
 * 
 * @author gaoxianglong
 */
@Repository
public class UserDaoImpl implements UserDao {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private UserInfoMapper userInfoMapper;

	// @Resource
	private PropertyPlaceholderConfigurer propertyPlaceholderConfigurer;
	@Resource
	private SQLTemplate sqlTemplate;

	@Override
	public void setUserInfo(UserInfo user) throws Exception {
		final String sql = "insert into userinfo_test(uid,userName) values(" + user.getUid() + ",?)";
		jdbcTemplate.update(sql, new Object[] { user.getUserName() });
	}

	@Override
	public List<UserInfo> getUserInfo(long uid) throws Exception {
		final String sql = "select username from userinfo_test where uid = " + uid + "";
		return jdbcTemplate.query(sql, userInfoMapper);
	}

	@Override
	public void changeUserInfo(UserInfo user) throws Exception {
		final String sql = propertyPlaceholderConfigurer.getSql("changeUserInfo", user.getUid());
		jdbcTemplate.update(sql, new Object[] { user.getUserName() });
	}

	@Override
	public void setUserInfo(Map<String, Object> params) throws Exception {
		final String sql = sqlTemplate.getSql("setUserInfo", params);
		jdbcTemplate.update(sql);
	}

	@Override
	public List<UserInfo> getUserInfo(Map<String, Object> params) throws Exception {
		final String sql = sqlTemplate.getSql("getUserInfo", params);
		return jdbcTemplate.query(sql, userInfoMapper);
	}

	@Override
	public void changeUserInfo(Map<String, Object> params) throws Exception {
		final String sql = sqlTemplate.getSql("changeUserInfo", params);
		jdbcTemplate.update(sql);
	}
}