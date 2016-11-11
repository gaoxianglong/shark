/*
 * Copyright 1999-2101 gaoxianglong Group Holding Ltd.
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

import java.io.InputStreamReader;
import java.util.Properties;
import com.sharksharding.exception.FileNotFoundException;

/**
 * 加载版本号
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.0
 */
public class LoadVersion {
	public static String getVersion() {
		String version = null;
		final String PATH = "version.properties";
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(LoadSharkLogo.class.getClassLoader().getResourceAsStream(PATH)));
			version = properties.getProperty("version");
		} catch (Exception e) {
			throw new FileNotFoundException("can not find config");
		}
		return version;
	}
}