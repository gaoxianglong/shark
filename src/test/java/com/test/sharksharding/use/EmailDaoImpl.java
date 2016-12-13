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
 * email反向索引表Dao接口
 * 
 * @author gaoxianglong
 */
@Repository
public class EmailDaoImpl implements EmailDao {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private JdbcTemplate jdbcTemplate2;

	// @Resource
	private PropertyPlaceholderConfigurer property;

	@Resource
	private SQLTemplate sqlTemplate;

	@Resource
	private EmailInfoMapper emailMapper;

	@Override
	public void setEmail(EmailInfo email) throws Exception {
		final String sql = property.getSql("insertEmail", email.getEmail_hash());
		jdbcTemplate.update(sql, new Object[] { email.getEmail(), email.getUid() });
	}

	@Override
	public void setEmail2(EmailInfo email) throws Exception {
		jdbcTemplate2.update("INSERT INTO email_test_0000(email_hash,email,uid) VALUES(?,?,?)",
				new Object[] { email.getEmail_hash(), email.getEmail(), email.getUid() });
	}

	@Override
	public List<EmailInfo> getEmail(EmailInfo email) throws Exception {
		final String sql = property.getSql("queryEmail", email.getEmail_hash());
		return jdbcTemplate.query(sql, new Object[] { email.getEmail() }, emailMapper);
	}

	@Override
	public List<EmailInfo> getEmail2(EmailInfo email) throws Exception {
		return jdbcTemplate2.query(
				"SELECT e.email_hash,e.email,e.uid FROM email_test_0000 e WHERE e.email_hash=? AND e.email=?",
				new Object[] { email.getEmail_hash(), email.getEmail() }, emailMapper);
	}

	@Override
	public void setEmail(Map<String, Object> params) throws Exception {
		final String sql = sqlTemplate.getSql("setEmail", params);
		jdbcTemplate.update(sql);
	}

	@Override
	public List<EmailInfo> getEmail(Map<String, Object> params) throws Exception {
		final String sql = sqlTemplate.getSql("getEmail", params);
		return jdbcTemplate.query(sql, emailMapper);
	}
}