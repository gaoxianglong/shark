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
package com.sharksharding.core.shard;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据路由入口
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.5
 */
@Aspect
public class SQLExecuterInterceptor {
	private SQLExecute sqlExecute;
	private Logger logger = LoggerFactory.getLogger(SQLExecuterInterceptor.class);

	private SQLExecuterInterceptor() {
		sqlExecute = new SQLExecute();
	}

	/**
	 * 基于Spring Aop的方式对org.springframework.jdbc.core.JdbcTemplate类下所有的update()
	 * 方法进行拦截
	 * 
	 * @author gaoxianglong
	 * 
	 * @param proceedingJoinPoint
	 *            委托对象的上下文信息
	 * 
	 * @exception Throwable
	 * 
	 * @return Object
	 */
	@Around("execution(* org.springframework.jdbc.core.JdbcTemplate.update*(..))")
	public Object interceptUpdateSQL(ProceedingJoinPoint proceedingJoinPoint) {
		Object result = null;
		/* 执行路由检测 */
		if (isRoute(proceedingJoinPoint)) {
			result = sqlExecute.execute(proceedingJoinPoint, true);
		} else {
			try {
				logger.debug("no need for routing");
				result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 基于Spring Aop的方式对org.springframework.jdbc.core.JdbcTemplate类下所有的query()
	 * 方法进行拦截
	 * 
	 * @author gaoxianglong
	 * 
	 * @param proceedingJoinPoint
	 *            委托对象的上下文信息
	 * 
	 * @exception Throwable
	 * 
	 * @return Object
	 */
	@Around("execution(* org.springframework.jdbc.core.JdbcTemplate.query*(..))")
	public Object interceptQuerySQL(ProceedingJoinPoint proceedingJoinPoint) {
		Object result = null;
		/* 执行路由检测 */
		if (isRoute(proceedingJoinPoint)) {
			result = sqlExecute.execute(proceedingJoinPoint, false);
		} else {
			try {
				logger.debug("no need for routing");
				result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 路由检测
	 * 
	 * @author gaoxianglong
	 * 
	 * @param proceedingJoinPoint
	 *            委托对象的上下文信息
	 * 
	 * @return boolean 是否路由检测结果
	 */
	public boolean isRoute(ProceedingJoinPoint proceedingJoinPoint) {
		/* 如果JdbcTemplate持有的不是SharkDatasourceGroup动态数据源,则不进行数据路由操作 */
		return ((JdbcTemplate) proceedingJoinPoint.getTarget()).getDataSource() instanceof SharkDatasourceGroup;
	}
}