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
package com.gxl.shark.util.xml;

import java.io.File;

/**
 * 生成数据源配置文件
 * 
 * @author gaoxianglong
 */
public interface CreateDSXml {
	/**
	 * 生成kratos的数据源信息配置文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @return boolean 生成结果
	 */
	public boolean createDatasourceXml(File savePath);
}