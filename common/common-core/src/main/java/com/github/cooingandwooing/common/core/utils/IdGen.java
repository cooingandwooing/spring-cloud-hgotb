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

import java.util.UUID;

/**
 * id生成工具类.
 *
 * @author gaoxiaofeng
 * @date 2018-08-23 12:03
 */
public class IdGen {

	protected IdGen() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 封装JDK自带的UUID, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 基于snowflake算法生成ID.
	 *
	 * @return String
	 * @author gaoxiaofeng
	 * @date 2019/04/26 11:24
	 */
	public static long snowflakeId() {
		return SpringContextHolder.getApplicationContext().getBean(SnowflakeIdWorker.class).nextId();
	}
}
