/*
 * Copyright 2015=2101 gaoxianglong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE=2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sharksharding.core.shard;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.SQLWarningException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.ResultSetSupportingSqlParameter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.sharksharding.util.ConfigValidate;
import com.sharksharding.util.LoadSharkLogo;
import com.sharksharding.util.LoadVersion;

/**
 * shark的 JdbcTemplate,扩展自org.springframework.jdbc.core.JdbcTemplate
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
public class SharkJdbcTemplate extends JdbcTemplate {
	/* master/slave读写的起始索引 */
	private String wr_index = "r0w0";
	/* 分库分表开关 */
	private boolean isShard = false;
	/* 分表模式,false为库内分片模式,true为一库一表分片模式 */
	private boolean shardMode = false;
	/* 片名是否连续,true为片名连续,false为非片名连续 */
	private boolean consistent = false;
	/* 分表规则 */
	private String dbRuleArray;
	/* 分库规则 */
	private String tbRuleArray;
	/* 分表后缀 */
	private String tbSuffix = "_0000";
	private SharkInfo sharkInfo;
	private Logger logger = LoggerFactory.getLogger(SharkJdbcTemplate.class);

	public SharkJdbcTemplate() {
		sharkInfo = SharkInfo.getShardInfo();
	}

	public void init() {
		sharkInfo.setWr_index(this.getWr_index());
		sharkInfo.setIsShard(this.isShard());
		sharkInfo.setShardMode(this.isShardMode());
		sharkInfo.setConsistent(this.isConsistent());
		sharkInfo.setDbRuleArray(this.getDbRuleArray());
		sharkInfo.setTbRuleArray(this.getTbRuleArray());
		sharkInfo.setTbSuffix(this.getTbSuffix());
		logger.debug("wr_index-->" + sharkInfo.getWr_index() + "\tisShard-->" + sharkInfo.getIsShard()
				+ "\tshardMode-->" + sharkInfo.getShardMode() + "\tconsistent" + sharkInfo.getConsistent()
				+ "\tdbRuleArray" + sharkInfo.getDbRuleArray() + "\ttbRuleArray" + sharkInfo.getTbRuleArray());
		logger.info(
				"\nWelcome to Shark\n" + LoadSharkLogo.load().replaceFirst("\\[version\\]", LoadVersion.getVersion()));
		/* 进行配置合法性验证 */
		new ConfigValidate(sharkInfo).validate();
	}

	public String getWr_index() {
		return wr_index;
	}

	public void setWr_index(String wr_index) {
		this.wr_index = wr_index;
	}

	public boolean isShard() {
		return isShard;
	}

	public void setIsShard(boolean isShard) {
		this.isShard = isShard;
	}

	public boolean isShardMode() {
		return shardMode;
	}

	public void setShardMode(boolean shardMode) {
		this.shardMode = shardMode;
	}

	public boolean isConsistent() {
		return consistent;
	}

	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
	}

	public String getDbRuleArray() {
		if (null != dbRuleArray) {
			dbRuleArray = dbRuleArray.toLowerCase().replaceAll("\\s", "");
		}
		return dbRuleArray;
	}

	public void setDbRuleArray(String dbRuleArray) {
		this.dbRuleArray = dbRuleArray;
	}

	public String getTbRuleArray() {
		if (null != tbRuleArray) {
			tbRuleArray = tbRuleArray.toLowerCase().replaceAll("\\s", "");
		}
		return tbRuleArray;
	}

	public void setTbRuleArray(String tbRuleArray) {
		this.tbRuleArray = tbRuleArray;
	}

	public String getTbSuffix() {
		return tbSuffix;
	}

	public void setTbSuffix(String tbSuffix) {
		this.tbSuffix = tbSuffix;
	}

	@Override
	public void setNativeJdbcExtractor(NativeJdbcExtractor extractor) {
		super.setNativeJdbcExtractor(extractor);
	}

	@Override
	public NativeJdbcExtractor getNativeJdbcExtractor() {
		return super.getNativeJdbcExtractor();
	}

	@Override
	public void setIgnoreWarnings(boolean ignoreWarnings) {
		super.setIgnoreWarnings(ignoreWarnings);
	}

	@Override
	public boolean isIgnoreWarnings() {
		return super.isIgnoreWarnings();
	}

	@Override
	public void setFetchSize(int fetchSize) {

		super.setFetchSize(fetchSize);
	}

	@Override
	public int getFetchSize() {

		return super.getFetchSize();
	}

	@Override
	public void setMaxRows(int maxRows) {

		super.setMaxRows(maxRows);
	}

	@Override
	public int getMaxRows() {

		return super.getMaxRows();
	}

	@Override
	public void setQueryTimeout(int queryTimeout) {

		super.setQueryTimeout(queryTimeout);
	}

	@Override
	public int getQueryTimeout() {

		return super.getQueryTimeout();
	}

	@Override
	public void setSkipResultsProcessing(boolean skipResultsProcessing) {

		super.setSkipResultsProcessing(skipResultsProcessing);
	}

	@Override
	public boolean isSkipResultsProcessing() {

		return super.isSkipResultsProcessing();
	}

	@Override
	public void setSkipUndeclaredResults(boolean skipUndeclaredResults) {

		super.setSkipUndeclaredResults(skipUndeclaredResults);
	}

	@Override
	public boolean isSkipUndeclaredResults() {

		return super.isSkipUndeclaredResults();
	}

	@Override
	public void setResultsMapCaseInsensitive(boolean resultsMapCaseInsensitive) {

		super.setResultsMapCaseInsensitive(resultsMapCaseInsensitive);
	}

	@Override
	public boolean isResultsMapCaseInsensitive() {

		return super.isResultsMapCaseInsensitive();
	}

	@Override
	public <T> T execute(ConnectionCallback<T> action) throws DataAccessException {

		return super.execute(action);
	}

	@Override
	protected Connection createConnectionProxy(Connection con) {

		return super.createConnectionProxy(con);
	}

	@Override
	public <T> T execute(StatementCallback<T> action) throws DataAccessException {

		return super.execute(action);
	}

	@Override
	public void execute(String sql) throws DataAccessException {

		super.execute(sql);
	}

	@Override
	public <T> T query(String sql, ResultSetExtractor<T> rse) throws DataAccessException {

		return super.query(sql, rse);
	}

	@Override
	public void query(String sql, RowCallbackHandler rch) throws DataAccessException {

		super.query(sql, rch);
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {

		return super.query(sql, rowMapper);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) throws DataAccessException {

		return super.queryForMap(sql);
	}

	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {

		return super.queryForObject(sql, rowMapper);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {

		return super.queryForObject(sql, requiredType);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {

		return super.queryForList(sql, elementType);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {

		return super.queryForList(sql);
	}

	@Override
	public SqlRowSet queryForRowSet(String sql) throws DataAccessException {

		return super.queryForRowSet(sql);
	}

	@Override
	public int update(String sql) throws DataAccessException {

		return super.update(sql);
	}

	@Override
	public int[] batchUpdate(String[] sql) throws DataAccessException {

		return super.batchUpdate(sql);
	}

	@Override
	public <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action) throws DataAccessException {

		return super.execute(psc, action);
	}

	@Override
	public <T> T execute(String sql, PreparedStatementCallback<T> action) throws DataAccessException {

		return super.execute(sql, action);
	}

	@Override
	public <T> T query(PreparedStatementCreator psc, PreparedStatementSetter pss, ResultSetExtractor<T> rse)
			throws DataAccessException {

		return super.query(psc, pss, rse);
	}

	@Override
	public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse) throws DataAccessException {

		return super.query(psc, rse);
	}

	@Override
	public <T> T query(String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws DataAccessException {

		return super.query(sql, pss, rse);
	}

	@Override
	public <T> T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse)
			throws DataAccessException {

		return super.query(sql, args, argTypes, rse);
	}

	@Override
	public <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse) throws DataAccessException {

		return super.query(sql, args, rse);
	}

	@Override
	public <T> T query(String sql, ResultSetExtractor<T> rse, Object... args) throws DataAccessException {

		return super.query(sql, rse, args);
	}

	@Override
	public void query(PreparedStatementCreator psc, RowCallbackHandler rch) throws DataAccessException {

		super.query(psc, rch);
	}

	@Override
	public void query(String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException {

		super.query(sql, pss, rch);
	}

	@Override
	public void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch) throws DataAccessException {

		super.query(sql, args, argTypes, rch);
	}

	@Override
	public void query(String sql, Object[] args, RowCallbackHandler rch) throws DataAccessException {

		super.query(sql, args, rch);
	}

	@Override
	public void query(String sql, RowCallbackHandler rch, Object... args) throws DataAccessException {

		super.query(sql, rch, args);
	}

	@Override
	public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper) throws DataAccessException {

		return super.query(psc, rowMapper);
	}

	@Override
	public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper)
			throws DataAccessException {

		return super.query(sql, pss, rowMapper);
	}

	@Override
	public <T> List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
			throws DataAccessException {

		return super.query(sql, args, argTypes, rowMapper);
	}

	@Override
	public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {

		return super.query(sql, args, rowMapper);
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {

		return super.query(sql, rowMapper, args);
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
			throws DataAccessException {

		return super.queryForObject(sql, args, argTypes, rowMapper);
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {

		return super.queryForObject(sql, args, rowMapper);
	}

	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {

		return super.queryForObject(sql, rowMapper, args);
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType)
			throws DataAccessException {

		return super.queryForObject(sql, args, argTypes, requiredType);
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {

		return super.queryForObject(sql, args, requiredType);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException {

		return super.queryForObject(sql, requiredType, args);
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) throws DataAccessException {

		return super.queryForMap(sql, args, argTypes);
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {

		return super.queryForMap(sql, args);
	}

	@Override
	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType)
			throws DataAccessException {

		return super.queryForList(sql, args, argTypes, elementType);
	}

	@Override
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) throws DataAccessException {

		return super.queryForList(sql, args, elementType);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) throws DataAccessException {

		return super.queryForList(sql, elementType, args);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {

		return super.queryForList(sql, args, argTypes);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... args) throws DataAccessException {

		return super.queryForList(sql, args);
	}

	@Override
	public SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes) throws DataAccessException {

		return super.queryForRowSet(sql, args, argTypes);
	}

	@Override
	public SqlRowSet queryForRowSet(String sql, Object... args) throws DataAccessException {

		return super.queryForRowSet(sql, args);
	}

	@Override
	protected int update(PreparedStatementCreator psc, PreparedStatementSetter pss) throws DataAccessException {

		return super.update(psc, pss);
	}

	@Override
	public int update(PreparedStatementCreator psc) throws DataAccessException {

		return super.update(psc);
	}

	@Override
	public int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder) throws DataAccessException {

		return super.update(psc, generatedKeyHolder);
	}

	@Override
	public int update(String sql, PreparedStatementSetter pss) throws DataAccessException {

		return super.update(sql, pss);
	}

	@Override
	public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {

		return super.update(sql, args, argTypes);
	}

	@Override
	public int update(String sql, Object... args) throws DataAccessException {

		return super.update(sql, args);
	}

	@Override
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) throws DataAccessException {

		return super.batchUpdate(sql, pss);
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs) throws DataAccessException {

		return super.batchUpdate(sql, batchArgs);
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs, int[] argTypes) throws DataAccessException {

		return super.batchUpdate(sql, batchArgs, argTypes);
	}

	@Override
	public <T> int[][] batchUpdate(String sql, Collection<T> batchArgs, int batchSize,
			ParameterizedPreparedStatementSetter<T> pss) throws DataAccessException {

		return super.batchUpdate(sql, batchArgs, batchSize, pss);
	}

	@Override
	public <T> T execute(CallableStatementCreator csc, CallableStatementCallback<T> action) throws DataAccessException {

		return super.execute(csc, action);
	}

	@Override
	public <T> T execute(String callString, CallableStatementCallback<T> action) throws DataAccessException {

		return super.execute(callString, action);
	}

	@Override
	public Map<String, Object> call(CallableStatementCreator csc, List<SqlParameter> declaredParameters)
			throws DataAccessException {

		return super.call(csc, declaredParameters);
	}

	@Override
	protected Map<String, Object> extractReturnedResults(CallableStatement cs, List<SqlParameter> updateCountParameters,
			List<SqlParameter> resultSetParameters, int updateCount) throws SQLException {

		return super.extractReturnedResults(cs, updateCountParameters, resultSetParameters, updateCount);
	}

	@Override
	protected Map<String, Object> extractOutputParameters(CallableStatement cs, List<SqlParameter> parameters)
			throws SQLException {

		return super.extractOutputParameters(cs, parameters);
	}

	@Override
	protected Map<String, Object> processResultSet(ResultSet rs, ResultSetSupportingSqlParameter param)
			throws SQLException {

		return super.processResultSet(rs, param);
	}

	@Override
	protected RowMapper<Map<String, Object>> getColumnMapRowMapper() {

		return super.getColumnMapRowMapper();
	}

	@Override
	protected <T> RowMapper<T> getSingleColumnRowMapper(Class<T> requiredType) {

		return super.getSingleColumnRowMapper(requiredType);
	}

	@Override
	protected Map<String, Object> createResultsMap() {

		return super.createResultsMap();
	}

	@Override
	protected void applyStatementSettings(Statement stmt) throws SQLException {

		super.applyStatementSettings(stmt);
	}

	@Override
	protected PreparedStatementSetter newArgPreparedStatementSetter(Object[] args) {

		return super.newArgPreparedStatementSetter(args);
	}

	@Override
	protected PreparedStatementSetter newArgTypePreparedStatementSetter(Object[] args, int[] argTypes) {

		return super.newArgTypePreparedStatementSetter(args, argTypes);
	}

	@Override
	protected void handleWarnings(Statement stmt) throws SQLException {

		super.handleWarnings(stmt);
	}

	@Override
	protected void handleWarnings(SQLWarning warning) throws SQLWarningException {

		super.handleWarnings(warning);
	}
}