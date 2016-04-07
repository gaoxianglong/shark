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
package com.sharksharding.exception;

/**
 * 自动映射失败异常
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public class MapperException extends SharkRuntimeException {
	private static final long serialVersionUID = -8301606060406419061L;

	public MapperException(String str) {
		super(str);
	}
}