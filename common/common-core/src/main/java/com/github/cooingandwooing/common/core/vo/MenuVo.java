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

package com.github.cooingandwooing.common.core.vo;

import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * @author gaoxiaofeng
 * @date 2018-08-28 20:40
 */
@Data
public class MenuVo extends BaseEntity<MenuVo> {

	/**
	 * 菜单名称.
	 */
	private String name;

	/**
	 * 菜单权限标识.
	 */
	private String permission;

	/**
	 * url.
	 */
	private String url;

	/**
	 * 父菜单ID.
	 */
	private String parentId;

	/**
	 * 图标.
	 */
	private String icon;

	/**
	 * 排序号.
	 */
	private String sort;

	/**
	 * 类型.
	 */
	private String type;

	/**
	 * 路径.
	 */
	private String path;

	/**
	 * react页面.
	 */
	private String component;
}
