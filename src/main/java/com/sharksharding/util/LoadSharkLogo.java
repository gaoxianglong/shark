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
package com.sharksharding.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.sharksharding.exception.FileNotFoundException;

/**
 * 加载shark的字符图片
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.0
 */
public class LoadSharkLogo {
	public static String load() {
		final String PATH = "shark-logo.txt";
		StringBuffer value = new StringBuffer();
		try (BufferedReader read = new BufferedReader(
				new InputStreamReader(LoadSharkLogo.class.getClassLoader().getResourceAsStream(PATH)))) {
			String str = null;
			while ((str = read.readLine()) != null) {
				value.append(str + "\n");
			}
		} catch (Exception e) {
			throw new FileNotFoundException("can not find config");
		}
		return value.toString();
	}
}
