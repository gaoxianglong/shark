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
package com.gxl.shark.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.UUID;
import org.junit.Test;

public class TmpManagerTest {
	public @Test void testTmp() {
		final String tmpdir = TmpManager.createTmp();
		System.out.println("tmpdir-->" + tmpdir);
		try (BufferedWriter writ = new BufferedWriter(new FileWriter(tmpdir))) {
			writ.write(UUID.randomUUID().toString());
			writ.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/* 按任意键退出 */
				System.in.read();
				// TmpManager.deleteTmp(tmpdir);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}