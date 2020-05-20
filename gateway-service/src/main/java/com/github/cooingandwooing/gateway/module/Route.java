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

package com.github.cooingandwooing.gateway.module;

import javax.validation.constraints.NotBlank;

import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * 路由信息.
 *
 * @author gaoxiaofeng
 * @date 2019/4/2 14:52
 */
@Data
public class Route extends BaseEntity<Route> {

	/**
	 * 路由ID.
	 */
	@NotBlank(message = "路由id不能为空")
	private String routeId;

	/**
	 * 路由名称.
	 */
	private String routeName;

	/**
	 * 断言. [
	 *   {
	 *     "name": "Path",
	 *     "args": {
	 *       "_genkey_0": "/api/msc/**"
	 *     }
	 *   }
	 * ]
	 */
	private String predicates;

	/**
	 * 过滤器.
	 * [
	 *   {
	 *     "name": "StripPrefix",
	 *     "args": {
	 *       "_genkey_0": "2"
	 *     }
	 *   },
	 *   {
	 *     "name": "RemoveRequestHeader",
	 *     "args": {
	 *       "_genkey_0": "Cookie",
	 *       "_genkey_1": "Set-Cookie"
	 *     }
	 *   }
	 * ]
	 */
	private String filters;

	/**
	 * URI lb://msc-service.
	 */
	@NotBlank(message = "路由URI不能为空")
	private String uri;

	/**
	 * 排序.
	 */
	private String sort;

	/**
	 * 启用禁用.
	 */
	@NotBlank(message = "路由状态不能为空")
	private String status;
}
