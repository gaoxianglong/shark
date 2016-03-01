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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import com.gxl.shark.core.shard.SharkJdbcTemplate;
import com.gxl.shark.sql.PropertyPlaceholderConfigurer;

@Repository
public class MessageDaoImpl implements MessageDao {
	@Resource
	private SharkJdbcTemplate jdbcTemplate;

	@Resource
	private PropertyPlaceholderConfigurer property;

	@Resource
	private MessageMapper messageMapper;

	@Override
	public void insertMessage(Message message) throws Exception {
		final String SQL = property.getSql("insertMessage", message.getUserinfo_test_id());
		jdbcTemplate.update(SQL, new Object[] { message.getMessage() });
	}

	@Override
	public List<Message> queryMessagebyUid(long uid) throws Exception {
		final String SQL = property.getSql("queryMessagebyUid", uid);
		return jdbcTemplate.query(SQL, messageMapper);
	}
}