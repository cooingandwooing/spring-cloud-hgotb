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

package com.github.cooingandwooing.user.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import com.github.cooingandwooing.user.api.constant.MenuConstant;
import com.github.cooingandwooing.user.api.module.Menu;

/**
 * 菜单工具类.
 *
 * @author gaoxiaofeng
 * @date 2018/10/28 15:57
 */
public class MenuUtil {

	protected MenuUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 增删改查权限.
	 *
	 * @param menu menu
	 * @return List
	 * @author gaoxiaofeng
	 * @date 2018/10/28 15:59
	 */
	public static List<Menu> initMenuPermission(Menu menu) {
		List<Menu> menus = new ArrayList<>();
		// 新增权限
		Menu add = new Menu();
		add.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode());
		add.setParentId(menu.getId());
		add.setName(MenuConstant.PERMISSION_ADD);
		add.setPermission(menu.getPermission() + MenuConstant.PERMISSION_SUFFIX_ADD);
		add.setSort(CommonConstant.DEFAULT_SORT);
		add.setType(MenuConstant.MENU_TYPE_PERMISSION);

		// 删除权限
		Menu del = new Menu();
		del.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode());
		del.setParentId(menu.getId());
		del.setName(MenuConstant.PERMISSION_DELETE);
		del.setPermission(menu.getPermission() + MenuConstant.PERMISSION_SUFFIX_DELETE);
		del.setSort(CommonConstant.DEFAULT_SORT);
		del.setType(MenuConstant.MENU_TYPE_PERMISSION);

		// 修改权限
		Menu edit = new Menu();
		edit.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode());
		edit.setParentId(menu.getId());
		edit.setName(MenuConstant.PERMISSION_MODIFY);
		edit.setPermission(menu.getPermission() + MenuConstant.PERMISSION_SUFFIX_MODIFY);
		edit.setSort(CommonConstant.DEFAULT_SORT);
		edit.setType(MenuConstant.MENU_TYPE_PERMISSION);

		menus.add(add);
		menus.add(del);
		menus.add(edit);
		return menus;
	}

	/**
	 * 获取Menu属性的map.
	 *
	 * @return LinkedHashMap
	 * @author gaoxiaofeng
	 * @date 2018/11/28 12:48
	 */
	public static LinkedHashMap<String, String> getMenuMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("id", "菜单id");
		map.put("name", "菜单名称");
		map.put("permission", "菜单权限标识");
		map.put("url", "url");
		map.put("redirect", "重定向url");
		map.put("parentId", "父菜单ID");
		map.put("icon", "图标");
		map.put("sort", "排序号");
		map.put("type", "类型");
		map.put("component", "模块");
		map.put("path", "路径");
		map.put("remark", "备注");
		map.put("creator", "创建人");
		map.put("createDate", "创建时间");
		map.put("modifier", "修改人");
		map.put("modifyDate", "修改时间");
		map.put("delFlag", "删除标记");
		map.put("applicationCode", "系统编码");
		map.put("tenantCode", "租户标识");
		return map;
	}
}
