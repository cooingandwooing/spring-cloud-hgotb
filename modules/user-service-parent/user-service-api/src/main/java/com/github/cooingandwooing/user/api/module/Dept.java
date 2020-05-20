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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * 部门.
 *
 * @author cooingandwooing
 * @date 2018/8/26 22:25
 */
@Data
public class Dept extends BaseEntity<Dept> {

	/**
	 * 部门名称.
	 */
	@NotBlank(message = "部门名称不能为空")
	private String deptName;

	/**
	 * 部门描述.
	 */
	private String deptDesc;

	/**
	 * 部门负责人.
	 */
	private String deptLeader;

	/**
	 * 父部门ID.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long parentId;

	/**
	 * 排序.
	 */
	private Integer sort;
}
