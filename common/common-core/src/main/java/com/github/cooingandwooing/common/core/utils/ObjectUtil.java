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
 * @author gaoxiaofeng.
 * @date 2018/11/26 22:47
 */
public class ObjectUtil {

	protected ObjectUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 将字符串转换为double,如果字符串为空或者null，则自动转换为0.0。.
	 *
	 * @param toConvert 需要转换的字符串
	 * @return double
	 */
	public static double obj2Double(Object toConvert) {
		if ((toConvert != null) && ((toConvert instanceof Double))) {
			return ((Double) toConvert).doubleValue();
		}
		double d = 0.0D;
		try {
			d = Double.parseDouble(String.valueOf(toConvert));
		}
		catch (Exception ex) {
		}
		return d;
	}
}
