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
package com.sharksharding.sql;

import java.util.List;

import com.sharksharding.exception.SqlParserException;
import com.sharksharding.sql.ast.SQLStatement;
import com.sharksharding.sql.ast.statement.SQLDeleteStatement;
import com.sharksharding.sql.ast.statement.SQLInsertStatement;
import com.sharksharding.sql.ast.statement.SQLSelect;
import com.sharksharding.sql.ast.statement.SQLSelectQueryBlock;
import com.sharksharding.sql.ast.statement.SQLSelectStatement;
import com.sharksharding.sql.ast.statement.SQLUpdateStatement;
import com.sharksharding.sql.dialect.mysql.parser.MySqlStatementParser;
import com.sharksharding.sql.parser.SQLStatementParser;

/**
 * 验证SQL语句WHERE条件后面是否带参数
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public class SQLIsWhereColumn {
	public static boolean isColumn(String sql) {
		boolean result = false;
		SQLStatementParser parser = new MySqlStatementParser(sql);
		List<SQLStatement> statements = parser.parseStatementList();
		if (!statements.isEmpty()) {
			SQLStatement statement = statements.get(0);
			if (statement instanceof SQLSelectStatement) {
				SQLSelectStatement selectStatement = (SQLSelectStatement) statement;
				SQLSelect sqlselect = selectStatement.getSelect();
				SQLSelectQueryBlock queryBlock = (SQLSelectQueryBlock) sqlselect.getQuery();
				try {
					queryBlock.getWhere_();
					result = true;
				} catch (SqlParserException e) {
					throw new SqlParserException("no condition");
				}
			} else if (statement instanceof SQLInsertStatement) {
				/* INSERT语句直接放行 */
				result = true;
			} else if (statement instanceof SQLDeleteStatement) {
				SQLDeleteStatement deleteStatement = (SQLDeleteStatement) statement;
				try {
					deleteStatement.getWhere_();
					result = true;
				} catch (SqlParserException e) {
					throw new SqlParserException("no condition");
				}
			} else if (statement instanceof SQLUpdateStatement) {
				SQLUpdateStatement updateStatement = (SQLUpdateStatement) statement;
				try {
					updateStatement.getWhere_();
					result = true;
				} catch (SqlParserException e) {
					throw new SqlParserException("no condition");
				}
			}
		}
		return result;
	}
}