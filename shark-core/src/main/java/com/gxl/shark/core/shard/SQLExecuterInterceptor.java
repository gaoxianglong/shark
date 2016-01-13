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
package com.gxl.shark.core.shard;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 数据路由入口
 * 
 * @author gaoxianglong
 */
@Aspect
public class SQLExecuterInterceptor {
	@Resource
	private SQLExecute sqlExecute;

	/**
	 * 基于SpringAop的方式对org.springframework.jdbc.core.JdbcTemplate下所有的update()方法进行拦截
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
	@Around("execution(* com.gxl.shark.core.shard.SharkJdbcTemplate.update*(..))")
	public Object interceptUpdateSQL(ProceedingJoinPoint proceedingJoinPoint) {
		return sqlExecute.execute(proceedingJoinPoint, true);
	}

	/**
	 * 基于SpringAop的方式对org.springframework.jdbc.core.JdbcTemplate下所有的query()方法进行拦截
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
	@Around("execution(* com.gxl.shark.core.shard.SharkJdbcTemplate.query*(..))")
	public Object interceptQuerySQL(ProceedingJoinPoint proceedingJoinPoint) {
		return sqlExecute.execute(proceedingJoinPoint, false);
	}
}