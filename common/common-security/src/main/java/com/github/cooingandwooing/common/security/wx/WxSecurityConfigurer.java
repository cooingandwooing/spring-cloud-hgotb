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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cooingandwooing.common.security.core.CustomUserDetailsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 微信登录配置
 *
 * @author gaoxiaofeng
 * @date 2019/07/05 19:29
 */
@Data
public class WxSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationEventPublisher defaultAuthenticationEventPublisher;

    private AuthenticationSuccessHandler wxLoginSuccessHandler;

    private CustomUserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) {
        // 微信登录过滤器
        WxAuthenticationFilter wxAuthenticationFilter = new WxAuthenticationFilter();
        wxAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        wxAuthenticationFilter.setAuthenticationSuccessHandler(wxLoginSuccessHandler);
        wxAuthenticationFilter.setEventPublisher(defaultAuthenticationEventPublisher);
        WxAuthenticationProvider wxAuthenticationProvider = new WxAuthenticationProvider();
        wxAuthenticationProvider.setCustomUserDetailsService(userDetailsService);
        // 增加微信登录的过滤器
        http.authenticationProvider(wxAuthenticationProvider).addFilterAfter(wxAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
