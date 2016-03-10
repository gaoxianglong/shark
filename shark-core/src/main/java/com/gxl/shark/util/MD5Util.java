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

import java.security.MessageDigest;
import com.gxl.shark.exception.UtilsRuntimeException;

/**
 * 生成32位的小写MD5码，主要用于校验redis cluster配置中心的配置信息比对
 * 
 * @author gaoxianglong
 * 
 * @version 1.3.7
 */
public class MD5Util {
	/**
	 * 生成32位的MD5码
	 *
	 * @author gaoxianglong
	 * 
	 * @param str
	 *            加密前的文本信息
	 * 
	 * @exception UtilsRuntimeException
	 * 
	 * @return String 生成的32位MD5码
	 */
	public static String toMd5Code(String str) {
		String result = null;
		if (null != str) {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				messageDigest.update(str.getBytes());
				byte[] values = messageDigest.digest();
				StringBuffer strBuffer = new StringBuffer();
				for (byte value : values) {
					int bt = value & 0xff;
					if (bt < 16)
						strBuffer.append(0);
					strBuffer.append(Integer.toHexString(bt));
				}
				result = strBuffer.toString();
			} catch (Exception e) {
				throw new UtilsRuntimeException("生成32位的MD5码失败[" + e.toString() + "]");
			}
		}
		return result;
	}
}