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

import com.sharksharding.exception.ResourceException;

/**
 * 生成SequenceId服务实现
 * 
 * @author gaoxianglong
 * 
 * @version 1.4.1
 */
public abstract class CreateSequenceIdServiceImpl implements CreateSequenceIdService {
	@Override
	public long getSequenceId(int idcNum, int type, long memData) {
		return -1L;
	}

	@Override
	public long getSequenceId(String rootPath, int idcNum, int type) throws ResourceException {
		return -1L;
	}
}