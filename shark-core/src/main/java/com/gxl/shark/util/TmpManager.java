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
package com.gxl.shark.util;

import java.io.File;

/**
 * 从配置中心获取配置信息后生成的临时文件管理
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class TmpManager {
	private static String tmpdir;

	/**
	 * 删除临时文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @param path
	 *            tem目录
	 * 
	 * @return boolean 删除结果
	 */
	public static boolean deleteTmp(String path) {
		boolean result = false;
		if (IsFileExist.exist(path)) {
			result = new File(path).delete();
		}
		return result;
	}

	/**
	 * 生成tmp文件全限定名
	 * 
	 * @author gaoxianglong
	 * 
	 * @return String tmp文件全限定名
	 */
	public static String createTmp() {
		tmpdir = System.getProperty("java.io.tmpdir") + "shark-" + System.currentTimeMillis() + ".xml";
		if (IsFileExist.exist(tmpdir)) {
			createTmp();
		}
		return tmpdir;
	}
}