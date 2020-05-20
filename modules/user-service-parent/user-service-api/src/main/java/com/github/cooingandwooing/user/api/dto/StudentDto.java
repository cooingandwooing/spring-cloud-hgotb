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
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cooingandwooing
 * @date 2019/07/10 18:07
 */
@Data
public class StudentDto implements Serializable {

	private static final long serialVersionUID = 2602249526687821147L;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;

	/**
	 * 用户id.
	 */
	@ApiModelProperty("用户id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long userId;

	/**
	 * 学生id.
	 */
	@ApiModelProperty("学生id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long studentId;

	/**
	 * 关系类型.
	 */
	@ApiModelProperty("关系类型")
	private Integer relationshipType;

	/**
	 * 学生姓名.
	 */
	@ApiModelProperty("学生姓名")
	private String studentName;

	/**
	 * 出生日期.
	 */
	@ApiModelProperty(value = "出生日期", dataType = "Date")
	private Date born;

	/**
	 * 电话号码.
	 */
	@ApiModelProperty(value = "电话", example = "15521089184")
	private String phone;

	/**
	 * 微信.
	 */
	@ApiModelProperty("微信")
	private String wechat;

	/**
	 * 性别.
	 */
	@ApiModelProperty(value = "性别，0：男，1：女", dataType = "Integer", example = "0")
	private Integer sex;

	/**
	 * 家庭住址.
	 */
	@ApiModelProperty("家庭住址")
	private String address;

	/**
	 * 就读年级.
	 */
	@ApiModelProperty("就读年级ID")
	private String grade;


	/**
	 * 就读年级.
	 */
	@ApiModelProperty("就读年级名称")
	private String gradeName;

	/**
	 * 就读学校.
	 */
	@ApiModelProperty("就读学校")
	private String school;

	/**
	 * 就读城市.
	 */
	@ApiModelProperty("就读城市ID")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long cityId;

	/**
	 * 县id.
	 */
	@ApiModelProperty("就读区县ID")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long countyId;
}
