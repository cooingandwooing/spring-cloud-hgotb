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

package com.github.cooingandwooing.gateway.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.cooingandwooing.gateway.serializer.AccessTokenJacksonSerializer;
import lombok.Data;
/**
 * 封装access_token.
 *
 * @author gaoxiaofeng
 * @date 2019/5/19 15:43
 */
@Data
@JsonSerialize(using = AccessTokenJacksonSerializer.class)
public class AccessToken implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * access_token.
	 */
	@JsonProperty("access_token")
	private String accessToken;

	/**
	 * 超时时间.
	 */
	@JsonProperty("expires_in")
	private Integer expiresIn;

	/**
	 * jti.
	 */
	private String jti;

	/**
	 * refresh_token.
	 */
	@JsonProperty("refresh_token")
	private String refreshToken;

	/**
	 * scope.
	 */
	private String scope;

	/**
	 * token_type.
	 */
	@JsonProperty("token_type")
	private String tokenType;

	/**
	 * 租户标识.
	 */
	private String tenantCode;

	/**
	 * 登录类型.
	 */
	private String loginType;
}
