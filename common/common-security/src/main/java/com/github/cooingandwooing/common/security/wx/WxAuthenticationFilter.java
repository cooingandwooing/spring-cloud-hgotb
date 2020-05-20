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

package com.github.cooingandwooing.common.security.wx;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.cooingandwooing.common.security.constant.SecurityConstant;
import com.github.cooingandwooing.common.security.utils.GsonHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 微信登录filter，登录时支持传入用户的资料，如姓名、电话、性别等.
 *
 * @author gaoxiaofeng
 * @date 2019/07/05 19:32
 */
@Slf4j
public class WxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * 微信登录的code参数，后续用code换取openId和sessionKey.
	 */
	private static final String SPRING_SECURITY_FORM_WX_KEY = "code";

	@Getter
	@Setter
	private String wxParameter = SPRING_SECURITY_FORM_WX_KEY;

	@Getter
	@Setter
	private boolean postOnly = true;

	@Getter
	@Setter
	private AuthenticationEventPublisher eventPublisher;

	@Getter
	@Setter
	private AuthenticationEntryPoint authenticationEntryPoint;

	public WxAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstant.WX_TOKEN_URL, HttpMethod.POST.name()));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		// 获取code
		String code = obtainCode(request);
		if (code == null) {
			code = "";
		}
		code = code.trim();
		// 封装成token
		WxAuthenticationToken wxAuthenticationToken = new WxAuthenticationToken(code);
		// 封装其它基本信息
		setWxUserDetails(request, wxAuthenticationToken);
		setDetails(request, wxAuthenticationToken);
		Authentication authResult = null;
		try {
			// 认证
			authResult = this.getAuthenticationManager().authenticate(wxAuthenticationToken);
			logger.debug("Authentication success: " + authResult);
			// 认证成功
			eventPublisher.publishAuthenticationSuccess(authResult);
			SecurityContextHolder.getContext().setAuthentication(authResult);
		}
		catch (Exception failed) {
			SecurityContextHolder.clearContext();
			logger.debug("Authentication request failed: " + failed);
			eventPublisher.publishAuthenticationFailure(new BadCredentialsException(failed
				.getMessage(), failed), new PreAuthenticatedAuthenticationToken("access-token", "N/A"));
			try {
				authenticationEntryPoint
					.commence(request, response, new UsernameNotFoundException(failed.getMessage(), failed));
			}
			catch (Exception e) {
				logger.error("authenticationEntryPoint handle error:{}", failed);
			}
		}
		return authResult;
	}

	private String obtainCode(HttpServletRequest request) {
		return request.getParameter(wxParameter);
	}

	private void setDetails(HttpServletRequest request, WxAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * 设置姓名、性别、头像等信息.
	 *
	 * @param request     request
	 * @param authRequest authRequest
	 */
	private void setWxUserDetails(HttpServletRequest request, WxAuthenticationToken authRequest) {
		try {
			String result = IOUtils.toString(request.getReader());
			if (StringUtils.isNotBlank(result)) {
				WxUser user = GsonHelper.getInstance().fromJson(result, WxUser.class);
				authRequest.setWxUser(user);
			}
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
