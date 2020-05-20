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

import com.github.cooingandwooing.common.security.mobile.MobileSecurityConfigurer;
import com.github.cooingandwooing.common.security.properties.FilterIgnorePropertiesConfig;
import com.github.cooingandwooing.common.security.wx.WxSecurityConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * 资源服务器配置.
 *
 * @author gaoxiaofeng
 * @date 2019-03-15 11:37
 */
@Configuration
@EnableResourceServer
public class CustomResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "resource_id";

	/**
	 * 开放权限的URL.
	 */
	private final FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

	/**
	 * 手机登录配置.
	 */
	private final MobileSecurityConfigurer mobileSecurityConfigurer;

	/**
	 * 微信登录配置.
	 */
	private final WxSecurityConfigurer wxSecurityConfigurer;

	@Autowired
	public CustomResourceServerConfig(FilterIgnorePropertiesConfig filterIgnorePropertiesConfig, MobileSecurityConfigurer mobileSecurityConfigurer, WxSecurityConfigurer wxSecurityConfigurer) {
		this.filterIgnorePropertiesConfig = filterIgnorePropertiesConfig;
		this.mobileSecurityConfigurer = mobileSecurityConfigurer;
		this.wxSecurityConfigurer = wxSecurityConfigurer;
	}

	/**
	 * 重点，设置资源id，每一个Resource Server（一个微服务实例）设置一个resourceid.
	 * 再给client授权的时候，可以设置这个client可以访问哪一些微服务实例，如果没设置，就是对所有的resource都有访问权限。
	 * @param resources r
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		// Flag to indicate that only token-based authentication is allowed on these resources. stateless - the flag value (default true)
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		String[] ignores = new String[filterIgnorePropertiesConfig.getUrls().size()];
		http
			.csrf().disable()
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers(filterIgnorePropertiesConfig.getUrls().toArray(ignores)).permitAll()
			.anyRequest().authenticated()
			.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
		// 手机号登录
		http.apply(mobileSecurityConfigurer);
		// 微信登录
		http.apply(wxSecurityConfigurer);
	}
}
