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

package com.github.cooingandwooing.user.service;

import java.util.ArrayList;
import java.util.List;

import com.github.cooingandwooing.common.core.service.CrudService;
import com.github.cooingandwooing.common.core.utils.IdGen;
import com.github.cooingandwooing.user.api.module.RoleMenu;
import com.github.cooingandwooing.user.mapper.RoleMenuMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author cooingandwooing
 * @date 2018/8/26 22:47
 */
@AllArgsConstructor
@Service
public class RoleMenuService extends CrudService<RoleMenuMapper, RoleMenu> {

	private final RoleMenuMapper roleMenuMapper;

	/**
	 * @param role  role
	 * @param menus 菜单ID集合
	 * @return int
	 * @author cooingandwooing
	 * @date 2018/10/28 14:29
	 */
	@Transactional
	@CacheEvict(value = "menu", allEntries = true)
	public int saveRoleMenus(Long role, List<Long> menus) {
		int update = -1;
		if (CollectionUtils.isNotEmpty(menus)) {
			// 删除旧的管理数据
			roleMenuMapper.deleteByRoleId(role);
			List<RoleMenu> roleMenus = new ArrayList<>();
			for (Long menuId : menus) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setId(IdGen.snowflakeId());
				roleMenu.setRoleId(role);
				roleMenu.setMenuId(menuId);
				roleMenus.add(roleMenu);
			}
			update = roleMenuMapper.insertBatch(roleMenus);
		}
		return update;
	}

	/**
	 * 批量保存.
	 *
	 * @param roleMenus roleMenus
	 * @return int
	 * @author cooingandwooing
	 * @date 2018/10/30 19:59
	 */
	@Transactional
	public int insertBatch(List<RoleMenu> roleMenus) {
		return roleMenuMapper.insertBatch(roleMenus);
	}

	/**
	 * 根据roleId查询.
	 *
	 * @param roleMenu roleMenu
	 * @return List
	 * @author cooingandwooing
	 * @date 2019/09/02 22:22:12
	 */
	public List<RoleMenu> getByRoleId(RoleMenu roleMenu) {
		return roleMenuMapper.getByRoleId(roleMenu);
	}

	/**
	 * 根据menuId查询.
	 *
	 * @param roleMenu roleMenu
	 * @return List
	 * @author cooingandwooing
	 * @date 2019-09-14 15:49
	 */
	public List<RoleMenu> getByMenuId(RoleMenu roleMenu) {
		return roleMenuMapper.getByMenuId(roleMenu);
	}

	/**
	 * 根据menuId列表查询.
	 *
	 * @param roleMenus roleMenus
	 * @return List
	 * @author cooingandwooing
	 * @date 2019-09-14 16:00
	 */
	public List<RoleMenu> getByMenuIds(List<RoleMenu> roleMenus) {
		return roleMenuMapper.getByMenuIds(roleMenus);
	}
}
