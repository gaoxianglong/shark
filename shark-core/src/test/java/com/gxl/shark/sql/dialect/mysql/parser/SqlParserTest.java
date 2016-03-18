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
 * See the License for the specific language governing permissions AND
 * limitations under the License.
 */
package com.gxl.shark.sql.dialect.mysql.parser;

import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

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
 * 利用Druid的SqlParser解析出数据库表名和路由条件
 * 
 * @author gaoxianglong
 */
public class SqlParserTest {
	public @Test void parserbySelect() throws Exception {
		final String SQL = "SELECT * FROM userinfo_test WHERE uid = 10000 AND name = gaoxianglong";
		/* 生成AST抽象语法树 */
		SQLStatementParser parser = new MySqlStatementParser(SQL);
		List<SQLStatement> statements = parser.parseStatementList();
		if (0 < statements.size()) {
			SQLStatement statement = statements.get(0);
			if (statement instanceof SQLSelectStatement) {
				SQLSelectStatement selectStatement = (SQLSelectStatement) statement;
				SQLSelect sqlselect = selectStatement.getSelect();
				SQLSelectQueryBlock queryBlock = (SQLSelectQueryBlock) sqlselect.getQuery();
				List<String> values = queryBlock.getWhere_();
				System.out.println("tabName-->" + queryBlock.getFrom());
				System.out.println("routeKey-->" + values.get(0));
				System.out.println("routeValue-->" + values.get(2));
				System.out.println(queryBlock.getWhere());
			}
		}
	}

	public @Test void parserbyInsert() throws Exception {
		String sql = "INSERT INTO userinfo_test(uid,name) VALUES(10000,gaoxianglong)";
		/* 生成AST抽象语法树 */
		SQLStatementParser parser = new MySqlStatementParser(sql);
		List<SQLStatement> statements = parser.parseStatementList();
		if (0 < statements.size()) {
			SQLStatement statement = statements.get(0);
			if (statement instanceof SQLInsertStatement) {
				SQLInsertStatement insertStatement = (SQLInsertStatement) statement;
				System.out.println("tabName-->" + insertStatement.getTableName());
				System.out.println("routeKey-->" + insertStatement.getColumns().get(0));
				System.out.println("routeValue-->" + insertStatement.getValues().getValues().get(0));
			}
		}
	}

	public @Test void parserbyUpdate() throws Exception {
		String sql = "UPDATE userinfo_test SET sex = ? WHERE uid=10000 AND name=gaoxianglong";
		/* 生成AST抽象语法树 */
		SQLStatementParser parser = new MySqlStatementParser(sql);
		List<SQLStatement> statements = parser.parseStatementList();
		if (0 < statements.size()) {
			SQLStatement statement = statements.get(0);
			if (statement instanceof SQLUpdateStatement) {
				SQLUpdateStatement updateStatement = (SQLUpdateStatement) statement;
				List<String> values = updateStatement.getWhere_();
				System.out.println("tabName-->" + updateStatement.getTableName());
				System.out.println("routeKey-->" + values.get(0));
				System.out.println("routeValue-->" + values.get(2));
				System.out.println(updateStatement.getWhere());
			}
		}
	}

	public @Test void parserbyDelete() throws Exception {
		String sql = "DELETE FROM userinfo_test WHERE uid=10000 AND name=gaoxianglong";
		/* 生成AST抽象语法树 */
		SQLStatementParser parser = new MySqlStatementParser(sql);
		List<SQLStatement> statements = parser.parseStatementList();
		if (0 < statements.size()) {
			SQLStatement statement = statements.get(0);
			if (statement instanceof SQLDeleteStatement) {
				SQLDeleteStatement deleteStatement = (SQLDeleteStatement) statement;
				List<String> values = deleteStatement.getWhere_();
				System.out.println("tabName-->" + deleteStatement.getTableName());
				System.out.println("routeKey-->" + values.get(0));
				System.out.println("routeValue-->" + values.get(2));
				System.out.println(deleteStatement.getWhere());
			}
		}
	}
}