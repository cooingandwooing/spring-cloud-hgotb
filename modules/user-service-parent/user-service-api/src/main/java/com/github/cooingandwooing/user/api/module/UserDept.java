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

import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * 用户部门关系.
 *
 * @author cooingandwooing
 * @date 2018/10/27 10:23
 */
@Data
public class UserDept extends BaseEntity<UserDept> {

	/**
	 * 用户ID.
	 */
	private String userId;

	/**
	 * 部门ID.
	 */
	private String deptId;
}
