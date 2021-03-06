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

package com.github.cooingandwooing.auth.config;

import javax.sql.DataSource;

import com.github.cooingandwooing.auth.security.CustomTokenConverter;
import com.github.cooingandwooing.common.security.core.ClientDetailsServiceImpl;
import com.github.cooingandwooing.common.security.exceptions.CustomOauthException;
import com.github.cooingandwooing.user.api.feign.UserServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 授权服务器配置.
 *
 * @author gaoxiaofeng
 * @date 2019-03-14 11:40
 */
@Configuration
public class CustomAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

	/**
	 * 认证管理器.
	 */
	private final AuthenticationManager authenticationManager;

	/**
	 * redis连接工厂.
	 */
	private final RedisConnectionFactory redisConnectionFactory;

	/**
	 * 数据源.
	 */
	private final DataSource dataSource;

	/**
	 * key配置信息.
	 */
	private final KeyProperties keyProperties;

	private final UserServiceClient userServiceClient;

	private OAuth2RequestFactory oAuth2RequestFactory;

	@Autowired
	public CustomAuthorizationServerConfigurer(AuthenticationManager authenticationManager,
		RedisConnectionFactory redisConnectionFactory,
		DataSource dataSource,
		KeyProperties keyProperties,
		UserServiceClient userServiceClient) {
		this.authenticationManager = authenticationManager;
		this.redisConnectionFactory = redisConnectionFactory;
		this.dataSource = dataSource;
		this.keyProperties = keyProperties;
		this.userServiceClient = userServiceClient;
	}

	/**
	 * 将token存储到redis.
	 * RedisTokenStore只要该token不过期、永远使用的都是一个token、不知道有没有别的方法可以不这样、我找了好多都没找到、而JwtTokenStore则是每次都生成不同的token、感觉可以使用RedisTokenStore来储存token、然后自定义TokenEnhancer来生成token
	 * @return TokenStore
	 */
	@Bean
	public TokenStore tokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}

	/**
	 * token增强，使用非对称加密算法来对Token进行签名.
	 * JwtAccessTokenConverter token生成中的处理
	 * token生产 @see DefaultTokenServices
	 * @return JwtAccessTokenConverter
	 */
	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyProperties.getKeyStore()
			.getLocation(), keyProperties.getKeyStore().getPassword().toCharArray());
		CustomTokenConverter converter = new CustomTokenConverter();
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyProperties.getKeyStore().getAlias()));
		return converter;
	}

	/**
	 * 使用自定义的JdbcClientDetailsService客户端详情服务.
	 *
	 * @return ClientDetailsService
	 */
	@Bean
	public ClientDetailsService clientDetails() {
		return new ClientDetailsServiceImpl(dataSource);
	}

	/**
	 * 从数据库加载客户端信息.
	 *
	 * @param clients clients
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetails());
	}

	/**
	 * 配置TokenStore、Token增强、认证管理器以及异常处理.
	 *
	 * @param endpoints endpoints
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		oAuth2RequestFactory = endpoints.getOAuth2RequestFactory();
		endpoints
			// 将token存储到redis
			.tokenStore(tokenStore())
			// token增强
			.tokenEnhancer(jwtTokenEnhancer())
			// 认证管理器
			.authenticationManager(authenticationManager)
			// 异常处理
			.exceptionTranslator(e -> {
				if (e instanceof OAuth2Exception) {
					OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
					return ResponseEntity
						.status(oAuth2Exception.getHttpErrorCode())
						.body(new CustomOauthException(oAuth2Exception.getMessage(), oAuth2Exception
							.getHttpErrorCode()));
				}
				else {
					throw e;
				}
			});
	}

	/**
	 * 配置认证规则，哪些需要认证哪些不需要.
	 *
	 * @param oauthServer oauthServer
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
			.passwordEncoder(new BCryptPasswordEncoder())
			// 开启/oauth/token_key验证端口无权限访问 公钥（如果有的话）会由认证服务器通过 /oauth/token_key 提供，这个地址默认情况下是通过 “deenyAll()” 来保护的。你可以向 AuthorizationServerSecurityConfigurer 注入一个标准的 SpEL 表达式（比如 “permitAll()” 就可以，因为那是公钥）来放开保护限制。
			.tokenKeyAccess("permitAll()")
			// 开启/oauth/check_token验证端口认证权限访问
			.checkTokenAccess("isAuthenticated()")
			.allowFormAuthenticationForClients(); // 主要是让/oauth/token支持client_id以及client_secret作登录认证
		//.addTokenEndpointAuthenticationFilter(new CustomTokenEndpointAuthenticationFilter(authenticationManager, oAuth2RequestFactory, userServiceClient)); //登录成功后的处理，如记录登录日志 通过调用security. .addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);方法，将拦截器放入到认证链条中
	}
}

