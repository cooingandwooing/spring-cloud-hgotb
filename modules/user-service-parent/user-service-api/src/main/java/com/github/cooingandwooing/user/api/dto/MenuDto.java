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
import com.github.cooingandwooing.user.api.module.Menu;
import lombok.Data;

/**
 * 菜单dto.
 *
 * @author cooingandwooing
 * @date 2018-09-13 20:39
 */
@Data
public class MenuDto extends TreeEntity<MenuDto> {

	/**
	 * 父菜单ID.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long parentId;

	private String icon;

	private String name;

	private String url;

	private String redirect;

	private boolean spread = false;

	private String path;

	private String component;

	private String authority;

	private String code;

	private Integer type;

	private String label;

	private String[] roles;

	private String remark;

	public MenuDto(Menu menu) {
		this.id = menu.getId();
		this.parentId = menu.getParentId();
		this.icon = menu.getIcon();
		this.name = menu.getName();
		this.url = menu.getUrl();
		this.type = menu.getType();
		this.label = menu.getName();
		this.sort = Integer.parseInt(menu.getSort());
		this.component = menu.getComponent();
		this.path = menu.getPath();
		this.redirect = menu.getRedirect();
		this.remark = menu.getRemark();
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}
}
