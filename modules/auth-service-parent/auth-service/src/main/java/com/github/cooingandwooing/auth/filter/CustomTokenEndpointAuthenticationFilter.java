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

package com.github.cooingandwooing.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.cooingandwooing.auth.model.CustomUserDetails;
import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.constant.ServiceConstant;
import com.github.cooingandwooing.common.core.model.Log;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import com.github.cooingandwooing.user.api.feign.UserServiceClient;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;


/**
 * 登录成功后的处理，如记录登录日志.
 * 没有放入过滤器链中 没有启用 @see 	CustomAuthorizationServerConfigurer	//.addTokenEndpointAuthenticationFilter(new CustomTokenEndpointAuthenticationFilter(authenticationManager, oAuth2RequestFactory, userServiceClient)); //登录成功后的处理，如记录登录日志 通过调用security. .addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);方法，将拦截器放入到认证链条中
 * @author gaoxiaofeng
 * @date 2019-10-11 12:08
 */
@Slf4j
public class CustomTokenEndpointAuthenticationFilter extends TokenEndpointAuthenticationFilter {

	private UserServiceClient userServiceClient;

	public CustomTokenEndpointAuthenticationFilter(AuthenticationManager authenticationManager,
		OAuth2RequestFactory oAuth2RequestFactory, UserServiceClient userServiceClient) {
		super(authenticationManager, oAuth2RequestFactory);
		this.userServiceClient = userServiceClient;
	}

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		// 登录成功后的处理
		log.info("CustomTokenEndpointAuthenticationFilter onSuccessfulAuthentication");
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String tenantCode = userDetails.getTenantCode();
		String username = userDetails.getUsername();
		log.info("CustomTokenEndpointAuthenticationFilter 登录成功, tenantCode: {}, username: {}", tenantCode, username);
		// 记录日志
		Log logInfo = new Log();
		logInfo.setCommonValue(username, SysUtil.getSysCode(), tenantCode);
		logInfo.setTitle("用户登录");
		logInfo.setType(CommonConstant.STATUS_NORMAL);
		logInfo.setMethod(request.getMethod());
		logInfo.setTime(String.valueOf(System.currentTimeMillis() - userDetails.getStart()));
		logInfo.setRequestUri(request.getRequestURI());
		// 获取ip、浏览器信息
		logInfo.setIp(request.getRemoteAddr());
		logInfo.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
		logInfo.setServiceId(ServiceConstant.AUTH_SERVICE);
		// 记录日志
		saveLoginSuccessLog(logInfo);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {
		// 认证前的特殊处理
		//		if (!condition) {
		//			throw new AuthenticationServiceException("condition not satisfied");
		//		}
		log.info("CustomTokenEndpointAuthenticationFilter doFilter");
		super.doFilter(req, res, chain);
	}

	/**
	 * 异步记录登录日志.
	 *
	 * @author gaoxiaofeng
	 * @date 2019/05/30 23:30
	 */
	@Async
	public void saveLoginSuccessLog(Log logInfo) {
		try {
			userServiceClient.saveLog(logInfo);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
