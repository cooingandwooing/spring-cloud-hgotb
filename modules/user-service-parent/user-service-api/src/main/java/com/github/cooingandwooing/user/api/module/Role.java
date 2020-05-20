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

import javax.validation.constraints.NotBlank;

import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * 角色.
 *
 * @author cooingandwooing
 * @date 2018-08-25 13:58
 */
@Data
public class Role extends BaseEntity<Role> {

	@NotBlank(message = "角色名称不能为空")
	private String roleName;

	@NotBlank(message = "角色标识不能为空")
	private String roleCode;

	private String roleDesc;

	private Integer status;

	private String deptName;

	private String menuIds;

	/**
	 * 是否默认 0-否，1-是.
	 */
	private Integer isDefault;
}
