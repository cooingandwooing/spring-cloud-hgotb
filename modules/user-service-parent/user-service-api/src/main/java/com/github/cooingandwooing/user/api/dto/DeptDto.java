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

package com.github.cooingandwooing.user.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.cooingandwooing.common.core.persistence.TreeEntity;
import com.github.cooingandwooing.user.api.module.Dept;
import lombok.Data;

/**
 * 部门dto.
 *
 * @author cooingandwooing
 * @date 2018-10-25 12:49
 */
@Data
public class DeptDto extends TreeEntity<DeptDto> {

	/**
	 * 部门名称.
	 */
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

	public DeptDto(Dept dept) {
		this.id = dept.getId();
		this.deptName = dept.getDeptName();
		this.deptDesc = dept.getDeptDesc();
		this.deptLeader = dept.getDeptLeader();
		this.parentId = dept.getParentId();
		this.sort = dept.getSort();
		this.creator = dept.getCreator();
		this.createDate = dept.getCreateDate();
		this.modifier = dept.getModifier();
		this.modifyDate = dept.getModifyDate();
	}
}
