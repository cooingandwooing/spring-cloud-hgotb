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

package com.github.cooingandwooing.common.core.exceptions;

/**
 * 验证码错误异常.
 *
 * @author gaoxiaofeng
 * @date 2019/3/18 16:46
 */
public class InvalidValidateCodeException extends CommonException {

	private static final long serialVersionUID = -7285211528095468156L;

	public InvalidValidateCodeException() {
	}

	public InvalidValidateCodeException(String msg) {
		super(msg);
	}
}
