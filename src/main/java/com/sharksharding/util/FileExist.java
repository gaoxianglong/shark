/*
 * Copyright 1999-2101 Alibaba Group Holding Ltd.
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
package com.sharksharding.util;

import java.io.File;

/**
 * 验证java.io.tmpdir临时目录下得文件是否存在
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class FileExist {
	public @Deprecated static boolean exist(String path) {
		boolean result = false;
		if (new File(path).exists()) {
			result = true;
		}
		return result;
	}

	public static boolean exists(String path) {
		return new File(path).exists();
	}
}