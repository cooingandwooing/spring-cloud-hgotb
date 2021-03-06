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

package com.github.cooingandwooing.common.security.mobile;

import java.io.Serializable;

import lombok.Data;

/**
 * @author gaoxiaofeng
 * @date 2019/08/04 13:28
 */
@Data
public class MobileUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 姓名.
	 */
	private String name;

	/**
	 * 性别.
	 */
	private Integer sex;

	/**
	 * 头像地址.
	 */
	private String avatarUrl;

	/**
	 * 详细描述.
	 */
	private String userDesc;

	/**
	 * 国家.
	 */
	private String country;

	/**
	 * 省.
	 */
	private String province;

	/**
	 * 市.
	 */
	private String city;

	/**
	 * 语言.
	 */
	private String languang;
}
