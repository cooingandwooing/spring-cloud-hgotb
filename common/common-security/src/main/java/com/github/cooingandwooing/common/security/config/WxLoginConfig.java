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

package com.github.cooingandwooing.common.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cooingandwooing.common.security.core.CustomUserDetailsService;
import com.github.cooingandwooing.common.security.wx.WxLoginSuccessHandler;
import com.github.cooingandwooing.common.security.wx.WxSecurityConfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 微信登录相关配置.
 *
 * @author gaoxiaofeng
 * @date 2019/07/05 19:44
 */
@Configuration
public class WxLoginConfig {

	/**
	 * 配置微信登录.
	 *
	 * @return WxSecurityConfigurer
	 */
	@Bean
	public WxSecurityConfigurer wxSecurityConfigurer(@Lazy PasswordEncoder encoder, @Lazy ClientDetailsService clientDetailsService,
		@Lazy CustomUserDetailsService userDetailsService, @Lazy ObjectMapper objectMapper,
		@Lazy AuthorizationServerTokenServices defaultAuthorizationServerTokenServices) {
		WxSecurityConfigurer wxSecurityConfigurer = new WxSecurityConfigurer();
		wxSecurityConfigurer
			.setWxLoginSuccessHandler(wxLoginSuccessHandler(encoder, clientDetailsService, objectMapper, defaultAuthorizationServerTokenServices));
		wxSecurityConfigurer.setUserDetailsService(userDetailsService);
		return wxSecurityConfigurer;
	}

	/**
	 * 微信登录成功后的处理.
	 *
	 * @return AuthenticationSuccessHandler
	 */
	@Bean
	public AuthenticationSuccessHandler wxLoginSuccessHandler(PasswordEncoder encoder, ClientDetailsService clientDetailsService, ObjectMapper objectMapper,
		AuthorizationServerTokenServices defaultAuthorizationServerTokenServices) {
		return WxLoginSuccessHandler.builder()
			.objectMapper(objectMapper)
			.clientDetailsService(clientDetailsService)
			.passwordEncoder(encoder)
			.defaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices).build();
	}
}
