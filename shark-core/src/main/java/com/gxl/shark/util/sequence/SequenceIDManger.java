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
package com.gxl.shark.util.sequence;

/**
 * sequenceID的Manager类
 * 
 * @author gaoxianglong
 */
public class SequenceIDManger {
	private SequenceIDManger() {
	}

	/**
	 * 获取sequenceID
	 * 
	 * @author gaoxianglong
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房,1-3位长度
	 * 
	 * @param userType
	 *            类别,1-2位长度
	 * 
	 * @param memData
	 *            内存占位数量
	 * 
	 * @return long 返回生成的17-19位sequenceId
	 */
	public static long getSequenceId(int idcNum, int type, long memData) {
		return CreateSequenceIdService.createSequenceIdService().getSequenceId(idcNum, type, memData);
	}
}