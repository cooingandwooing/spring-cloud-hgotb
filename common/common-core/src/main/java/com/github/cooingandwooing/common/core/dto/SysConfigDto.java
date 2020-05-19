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

package com.github.cooingandwooing.common.core.dto;

import java.io.Serializable;

import lombok.Data;


/**
 * @author gaoxiaofeng
 * @date 2019/07/05 15:05
 */
@Data
public class SysConfigDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * fastDfs服务器的HTTP地址.
	 */
	private String fdfsHttpHost;

	/**
	 * 上传地址.
	 */
	private String uploadUrl;

	/**
	 * 默认头像.
	 */
	private String defaultAvatar;

	/**
	 * 管理员账号.
	 */
	private String adminUser;
}
