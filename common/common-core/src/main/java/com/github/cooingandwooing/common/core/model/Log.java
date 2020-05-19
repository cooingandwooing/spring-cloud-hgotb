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

package com.github.cooingandwooing.common.core.model;

import javax.validation.constraints.NotBlank;

import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;



/**
 * 日志.
 *
 * @author gaoxiaofeng
 * @date 2018/10/31 20:29
 */
@Data
public class Log extends BaseEntity<Log> {

	/**
	 * 日志类型.
	 */
	private Integer type;

	/**
	 * 日志标题.
	 */
	@NotBlank(message = "日志标题不能为空")
	private String title;

	/**
	 * 操作用户的IP地址.
	 */
	private String ip;

	/**
	 * 操作用户代理信息.
	 */
	private String userAgent;

	/**
	 * 操作的URI.
	 */
	private String requestUri;

	/**
	 * 操作的方式.
	 */
	private String method;

	/**
	 * 操作提交的数据.
	 */
	private String params;

	/**
	 * 异常信息.
	 */
	private String exception;

	/**
	 * 服务ID.
	 */
	private String serviceId;

	/**
	 * 耗时.
	 */
	private String time;
}
