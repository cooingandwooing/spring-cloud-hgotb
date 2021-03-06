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

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author cooingandwooing
 * @date 2018-09-13 17:18
 */
@Data
public class UserInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;

	/**
	 * 授权类型，1：用户名密码，2：手机号，3：邮箱，4：微信，5：QQ.
	 */
	private Integer identityType;

	/**
	 * 唯一标识，如用户名、手机号.
	 */
	private String identifier;

	/**
	 * 密码.
	 */
	@JsonIgnore
	private String credential;

	/**
	 * 姓名.
	 */
	private String name;

	/**
	 * 电话号码.
	 */
	private String phone;

	/**
	 * 头像对应的附件id.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long avatarId;

	/**
	 * 头像地址.
	 */
	private String avatarUrl;

	/**
	 * 邮箱.
	 */
	private String email;

	/**
	 * 性别.
	 */
	private Integer sex;

	/**
	 * 生日.
	 */
	private Date born;

	/**
	 * 部门名称.
	 */
	private String deptName;

	/**
	 * 部门ID.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long deptId;

	/**
	 * 备注.
	 */
	private String remark;

	/**
	 * 状态，0-启用，1-禁用.
	 */
	private Integer status;

	/**
	 * 权限信息.
	 */
	private String[] permissions;

	/**
	 * 角色信息.
	 */
	private String[] roles;

	/**
	 * 系统编号.
	 */
	private String applicationCode;

	/**
	 * 租户标识.
	 */
	private String tenantCode;

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
}
