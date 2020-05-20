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

package com.github.cooingandwooing.user.api.module;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * 用户授权信息.
 *
 * @author cooingandwooing
 * @date 2019/07/03 11:10
 */
@Data
public class UserAuths extends BaseEntity<UserAuths> {

	/**
	 * 用户id.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long userId;

	/**
	 * 授权类型，1：用户名密码，2：手机号，3：邮箱，4：微信，5：QQ.
	 */
	private Integer identityType;

	/**
	 * 唯一标识，如用户名、手机号.
	 */
	private String identifier;

	/**
	 * 密码凭证，跟授权类型有关，如密码、第三方系统的token等.
	 */
	private String credential;

}
