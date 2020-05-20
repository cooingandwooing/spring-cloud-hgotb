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

package com.github.cooingandwooing.user.api.constant;

/**
 * @author cooingandwooing
 * @date 2018/10/28 15:48
 */
public class MenuConstant {

	protected MenuConstant() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 菜单.
	 */
	public static final Integer MENU_TYPE_MENU = 0;

	/**
	 * 权限.
	 */
	public static final Integer MENU_TYPE_PERMISSION = 1;

	/**
	 * 新增.
	 */
	public static final String PERMISSION_ADD = "新增";

	/**
	 * 删除.
	 */
	public static final String PERMISSION_DELETE = "删除";

	/**
	 * 修改.
	 */
	public static final String PERMISSION_MODIFY = "修改";

	/**
	 * 新增.
	 */
	public static final String PERMISSION_SUFFIX_ADD = ":add";

	/**
	 * 删除.
	 */
	public static final String PERMISSION_SUFFIX_DELETE = ":del";

	/**
	 * 修改.
	 */
	public static final String PERMISSION_SUFFIX_MODIFY = ":edit";
}

