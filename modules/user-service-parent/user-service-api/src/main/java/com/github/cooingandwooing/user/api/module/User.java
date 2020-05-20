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

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * 用户基本信息.
 *
 * @author cooingandwooing
 * @date 2018-08-25 15:30
 */
@Data
public class User extends BaseEntity<User> {

	/**
	 * 姓名.
	 */
	private String name;

	/**
	 * 电话.
	 */
	@Pattern(regexp = "^\\d{11}$", message = "请输入11位手机号")
	private String phone;

	/**
	 * 头像id.
	 */
	private Long avatarId;

	/**
	 * 邮箱.
	 */
	@Email(message = "邮箱格式不正确")
	private String email;

	/**
	 * 性别.
	 */
	private Integer sex;

	/**
	 * 出生日期.
	 */
	private Date born;

	/**
	 * 描述.
	 */
	private String userDesc;

	/**
	 * 状态.
	 */
	private Integer status;

	/**
	 * 部门id.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long deptId;

	/**
	 * 角色列表.
	 */
	private List<Role> roleList;

	/**
	 * 角色.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private List<Long> role;

	/**
	 * 引导注册人.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long parentUid;

	/**
	 * 乡/镇.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long streetId;

	/**
	 * 县.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long countyId;

	/**
	 * 城市.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long cityId;

	/**
	 * 省份.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long provinceId;

	/**
	 * 最近登录时间.
	 */
	private Date loginTime;

	/**
	 * 用户归档时间.
	 */
	private Date lockTime;

	/**
	 * 微信.
	 */
	private String wechat;

	/**
	 * 家庭角色，参考UserStudentConstant.
	 */
	private Integer familyRole;
}
