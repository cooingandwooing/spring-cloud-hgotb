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
 * 断言.
 *
 * @author gaoxiaofeng
 * @date 2019-08-16 17:51
 */
@Data
public class Predicates extends BaseEntity<Predicates> {

	/**
	 * 路由ID.
	 */
	@NotBlank(message = "路由ID不能为空")
	private String routeId;

	/**
	 * 断言名称.
	 */
	@NotBlank(message = "predicates name不能为空")
	private String name;

	/**
	 * 断言参数.
	 */
	private String args;
}
