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

package com.github.cooingandwooing.gateway.constants;

/**
 * @author gaoxiaofeng
 * @date 2019/3/18 16:55
 */
public class GatewayConstant {

	protected GatewayConstant() {
		throw new UnsupportedOperationException();
	}

	/**
	 * oauth token.
	 */
	public static final String OAUTH_TOKEN_URL = "/oauth/token";

	/**
	 * 手机登录URL.
	 */
	public static final String MOBILE_TOKEN_URL = "/mobile/token";

	/**
	 * 微信登录URL.
	 */
	public static final String WX_TOKEN_URL = "/wx/token";

	/**
	 * 注册.
	 */
	public static final String REGISTER = "/user/register";

	/**
	 * 刷新token类型.
	 */
	public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

	/**
	 * 授权类型.
	 */
	public static final String GRANT_TYPE = "grant_type";

	/**
	 * 验证码已过期，请重新获取.
	 */
	public static final String EXPIRED_ERROR = "验证码已过期，请重新获取";

	/**
	 * 网关缓存的access_token的名称.
	 */
	public static final String GATEWAY_ACCESS_TOKENS = "gateway_access:";

	/**
	 * 网关缓存的refresh_token的名称.
	 */
	public static final String GATEWAY_REFRESH_TOKENS = "gateway_refresh:";

	/**
	 * 默认系统编号.
	 */
	public static final String SYS_CODE = "POETRY";

	/**
	 * 默认租户编号.
	 */
	public static final String DEFAULT_TENANT_CODE = "gitee";

	public static final String TENANT_CODE_HEADER = "Tenant-Code";

}
