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

import java.util.List;

import com.github.cooingandwooing.common.core.persistence.CrudMapper;
import com.github.cooingandwooing.user.api.module.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 菜单mapper.
 *
 * @author cooingandwooing
 * @date 2018/8/26 22:34
 */
@Mapper
public interface MenuMapper extends CrudMapper<Menu> {

	/**
	 * 根据角色查找菜单.
	 *
	 * @param role       角色标识
	 * @param tenantCode 租户标识
	 * @return List
	 */
	List<Menu> findByRole(@Param("role") String role, @Param("tenantCode") String tenantCode);

	/**
	 * 批量插入.
	 *
	 * @param menus menus
	 * @return int
	 */
	int insertBatch(List<Menu> menus);

	/**
	 * 根据租户code删除.
	 * @param menu menu
	 * @return int
	 */
	int deleteByTenantCode(Menu menu);
}
