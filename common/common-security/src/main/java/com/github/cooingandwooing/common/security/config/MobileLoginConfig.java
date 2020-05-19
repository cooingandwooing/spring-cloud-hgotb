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
import com.github.cooingandwooing.common.security.mobile.MobileLoginSuccessHandler;
import com.github.cooingandwooing.common.security.mobile.MobileSecurityConfigurer;
import com.github.cooingandwooing.common.security.core.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 手机号登录配置
 *
 * @author gaoxiaofeng
 * @date 2019/6/23 15:18
 */
@Configuration
public class MobileLoginConfig {

    /**
     * 配置手机号登录
     * 采用懒加载是因为只有认证授权服务需要手机登录的相关配置
     *
     * @return MobileSecurityConfigurer
     */
    @Bean
    public MobileSecurityConfigurer mobileSecurityConfigurer(@Lazy PasswordEncoder encoder, @Lazy ClientDetailsService clientDetailsService,
                                                             @Lazy CustomUserDetailsService userDetailsService, @Lazy ObjectMapper objectMapper,
                                                             @Lazy AuthorizationServerTokenServices defaultAuthorizationServerTokenServices) {
        MobileSecurityConfigurer mobileSecurityConfigurer = new MobileSecurityConfigurer();
        mobileSecurityConfigurer.setMobileLoginSuccessHandler(mobileLoginSuccessHandler(encoder, clientDetailsService, objectMapper, defaultAuthorizationServerTokenServices));
        mobileSecurityConfigurer.setUserDetailsService(userDetailsService);
        return mobileSecurityConfigurer;
    }

    /**
     * 手机登录成功后的处理
     *
     * @return AuthenticationSuccessHandler
     */
    @Bean
    public AuthenticationSuccessHandler mobileLoginSuccessHandler(PasswordEncoder encoder, ClientDetailsService clientDetailsService, ObjectMapper objectMapper,
                                                                  AuthorizationServerTokenServices defaultAuthorizationServerTokenServices) {
        return MobileLoginSuccessHandler.builder()
                .objectMapper(objectMapper)
                .clientDetailsService(clientDetailsService)
                .passwordEncoder(encoder)
                .defaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices).build();
    }
}
