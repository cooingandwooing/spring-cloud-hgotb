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

package com.github.cooingandwooing.auth.controller;

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.exceptions.CommonException;
import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.web.BaseController;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**.
 * Authentication管理
 * @author gaoxiaofeng
 */
@RestController
@RequestMapping("/v1/authentication")
public class AuthenticationController extends BaseController {

	@Autowired
	@Qualifier("consumerTokenServices")
	private ConsumerTokenServices consumerTokenServices;

	/**
	 * 用户信息校验
	 *
	 * @param authentication 信息
	 * @return 用户信息
	 */
	@RequestMapping("user")
	public Object user(Authentication authentication) {
		return authentication.getPrincipal();
	}

	/**.
	 * 清除access_token
	 *
	 * @param request request
	 * @return ReturnT
	 */
	@PostMapping("removeToken")
	public ResponseBean<Boolean> removeToken(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		if (StringUtils.isBlank(accessToken)) {
			throw new CommonException("accessToken为空.");
		}
		if (accessToken.startsWith(CommonConstant.AUTHORIZATION_BEARER)) {
			accessToken = accessToken.split(CommonConstant.AUTHORIZATION_BEARER)[1];
		}
		return new ResponseBean<>(consumerTokenServices.revokeToken(accessToken));
	}
}
