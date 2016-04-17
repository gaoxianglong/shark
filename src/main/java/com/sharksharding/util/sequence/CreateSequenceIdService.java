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
package com.sharksharding.util.sequence;

/**
 * 生成SequenceId服务
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public interface CreateSequenceIdService {
	/**
	 * 获取mysql生成的sequenceID,其最大值不可超过二进制位数64位long类型的最大值9223372036854775807
	 * 
	 * @author gaoxianglong
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房,3位数字长度
	 * 
	 * @param type
	 *            业务类别,2位数字长度
	 * 
	 * @param memData
	 *            内存占位数量
	 * 
	 * @return long 返回生成的19位数字长度的sequenceId
	 */
	public long getSequenceId(int idcNum, int type, long memData);

	/**
	 * 获取zookeeper生成的sequenceID,其最大值不可超过二进制位数64位long类型的最大值9223372036854775807
	 * 
	 * @author gaoxianglong
	 * 
	 * @param rootPath
	 *            znode根目录
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房,3位数字长度
	 * 
	 * @param type
	 *            业务类别,6位数字长度
	 * 
	 * @param memData
	 *            内存占位数量
	 * 
	 * @return long 返回生成的19位数字长度的sequenceId
	 */
	public long getSequenceId(String rootPath, int idcNum, int type, long memData);
}