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
package com.test.sharksharding.sql.mysql.parser;

import org.junit.Test;
import com.sharksharding.sql.SQLIsWhereColumn;
import junit.framework.Assert;

/**
 * 验证SQL语句WHERE条件后面是否带参数
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public class SQLIsWhereColumnTest {
	public @Test void testIsColumn() {
		String sql = "SELECT c2,c3 FROM tab WHERE c1=?";
		Assert.assertTrue(SQLIsWhereColumn.isColumn(sql));
		sql = "UPDATE tab SET c2=?,c3=? WHERE c1=?";
		Assert.assertTrue(SQLIsWhereColumn.isColumn(sql));
		sql = "DELETE FROM tab WHERE c1=?";
		Assert.assertTrue(SQLIsWhereColumn.isColumn(sql));
	}
}