/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.cooingandwooing.common.core.utils;

/**
 * 文件工具类.
 *
 * @author gaoxiaofeng
 * @date 2018/10/30 22:05
 */
public class FileUtil {

	protected FileUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取文件后缀名.
	 *
	 * @param fileName fileName
	 * @return String
	 * @author tangyi
	 * @date 2018/10/30 22:05
	 */
	public static String getFileNameEx(String fileName) {
		if ((fileName != null) && (fileName.length() > 0)) {
			int dot = fileName.lastIndexOf('.');
			if ((dot > -1) && (dot < (fileName.length()))) {
				return fileName.substring(dot + 1, fileName.length());
			}
		}
		return "";
	}
}
