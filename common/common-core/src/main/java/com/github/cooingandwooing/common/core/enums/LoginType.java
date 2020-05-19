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

package com.github.cooingandwooing.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录类型.
 *
 * @author gaoxiaofeng
 * @date 2019/07/02 09:45
 */
@Getter
@AllArgsConstructor
public enum LoginType {

	/**
	 * 账号密码登录.
	 */
	PWD("PWD", "账号密码登录", "/oauth/token"),

	/**
	 * QQ登录.
	 */
	QQ("QQ", "QQ登录", "/mobile/token"),

	/**
	 * 验证码登录.
	 */
	SMS("SMS", "验证码登录", "/mobile/token"),

	/**
	 * 微信登录.
	 */
	WECHAT("WX", "微信登录", "/wx/token");

	/**
	 * 类型.
	 */
	private String type;

	/**
	 * 描述.
	 */
	private String description;

	/**
	 * 接口uri.
	 */
	private String uri;
}
