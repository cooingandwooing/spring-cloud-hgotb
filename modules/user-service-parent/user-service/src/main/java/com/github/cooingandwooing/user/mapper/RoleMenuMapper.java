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

package com.github.cooingandwooing.user.mapper;

import com.github.cooingandwooing.common.core.persistence.CrudMapper;
import com.github.cooingandwooing.user.api.module.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色菜单mapper
 *
 * @author cooingandwooing
 * @date 2018/8/26 22:34
 */
@Mapper
public interface RoleMenuMapper extends CrudMapper<RoleMenu> {

	/**
	 * 根据角色ID删除
	 *
	 * @param roleId 角色ID
	 * @return int
	 */
	int deleteByRoleId(Long roleId);

	/**
	 * 根据菜单ID删除
	 *
	 * @param menuId 菜单ID
	 * @return int
	 */
	int deleteByMenuId(Long menuId);

	/**
	 * 批量保存
	 *
	 * @param roleMenus roleMenus
	 * @return int
	 */
	int insertBatch(List<RoleMenu> roleMenus);

	/**
	 * 根据roleId查询
	 *
	 * @param roleMenu roleMenu
	 * @return List
	 * @author cooingandwooing
	 * @date 2019/09/02 22:22:51
	 */
	List<RoleMenu> getByRoleId(RoleMenu roleMenu);

	/**
	 * 根据menuId查询
	 *
	 * @param roleMenu roleMenu
	 * @return List
	 * @author cooingandwooing
	 * @date 2019-09-14 15:50
	 */
	List<RoleMenu> getByMenuId(RoleMenu roleMenu);

	/**
	 * 根据menuId列表查询
	 *
	 * @param roleMenus roleMenus
	 * @return List
	 * @author cooingandwooing
	 * @date 2019-09-14 16:00
	 */
	List<RoleMenu> getByMenuIds(List<RoleMenu> roleMenus);
}
