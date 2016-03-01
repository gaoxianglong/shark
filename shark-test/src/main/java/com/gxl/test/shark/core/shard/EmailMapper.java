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
package com.gxl.test.shark.core.shard;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Email实体映射类
 * 
 * @author gaoxianglong
 */
@Component
public class EmailMapper implements RowMapper<Email> {
	@Override
	public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
		Email email = new Email();
		email.setEmail(rs.getString("email"));
		email.setEmail_hash(rs.getLong("email_hash"));
		email.setUserinfo_Id(rs.getLong("userinfo_test_id"));
		return email;
	}
}