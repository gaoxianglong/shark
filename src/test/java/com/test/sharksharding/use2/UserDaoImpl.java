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
package com.test.sharksharding.use2;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sharksharding.core.shard.SharkJdbcTemplate;

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
	private UserInfoMapper userInfoMapper;

	@Override
	public void setUserInfo(UserInfo user) throws Exception {
		final String SQL = "insert into userinfo_test(uid,userName) values(" + user.getUid() + ",?)";
		jdbcTemplate.update(SQL, new Object[] { user.getUserName() });
	}

	@Override
	public List<UserInfo> getUserInfo(long uid) throws Exception {
		final String SQL = "select * from userinfo_test t where t.uid = " + uid + "";
		return jdbcTemplate.query(SQL, userInfoMapper);
	}

	@Override
	public List<UserInfo> getUserInfos() throws Exception {
		final String SQL = "select * from userinfo_test";
		return jdbcTemplate.query(SQL, userInfoMapper);
	}
}