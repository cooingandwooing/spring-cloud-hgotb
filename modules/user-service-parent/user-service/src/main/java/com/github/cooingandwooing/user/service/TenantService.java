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

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.service.CrudService;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import com.github.cooingandwooing.common.security.constant.SecurityConstant;
import com.github.cooingandwooing.user.api.constant.TenantConstant;
import com.github.cooingandwooing.user.api.enums.IdentityType;
import com.github.cooingandwooing.user.api.module.Menu;
import com.github.cooingandwooing.user.api.module.Role;
import com.github.cooingandwooing.user.api.module.Tenant;
import com.github.cooingandwooing.user.api.module.User;
import com.github.cooingandwooing.user.api.module.UserAuths;
import com.github.cooingandwooing.user.api.module.UserRole;
import com.github.cooingandwooing.user.mapper.TenantMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 租户Service.
 *
 * @author cooingandwooing
 * @date 2019/5/22 22:51
 */
@Slf4j
@AllArgsConstructor
@Service
public class TenantService extends CrudService<TenantMapper, Tenant> {

	private final UserService userService;

	private final UserAuthsService userAuthsService;

	private final UserRoleService userRoleService;

	private final RoleService roleService;

	private final MenuService menuService;

	/**
	 * 根据租户标识获取.
	 *
	 * @param tenantCode tenantCode
	 * @return Tenant
	 * @author cooingandwooing
	 * @date 2019/05/26 10:28
	 */
	@Cacheable(value = "tenant#" + CommonConstant.CACHE_EXPIRE, key = "#tenantCode")
	public Tenant getByTenantCode(String tenantCode) {
		return this.dao.getByTenantCode(tenantCode);
	}

	/**
	 * 新增租户，自动初始化租户管理员账号.
	 *
	 * @param tenant tenant
	 * @return int
	 * @author cooingandwooing
	 * @date 2019-09-02 11:41
	 */
	@Transactional
	@CacheEvict(value = "tenant", key = "#tenant.tenantCode")
	public int add(Tenant tenant) {
		return this.insert(tenant);
	}

	/**
	 * 更新.
	 *
	 * @param tenant tenant
	 * @return Tenant
	 * @author cooingandwooing
	 * @date 2019/05/26 10:28
	 */
	@Transactional
	@CacheEvict(value = "tenant", key = "#tenant.tenantCode")
	@Override
	public int update(Tenant tenant) {
		Integer status = tenant.getStatus();
		Tenant currentTenant = this.get(tenant);
		// 待审核 -> 审核通过
		if (currentTenant != null && currentTenant.getStatus().equals(TenantConstant.PENDING_AUDIT) && status
			.equals(TenantConstant.APPROVAL)) {
			log.info("待审核 -> 审核通过：{}", tenant.getTenantCode());
			// 用户基本信息
			User user = new User();
			user.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode(), tenant.getTenantCode());
			user.setStatus(CommonConstant.STATUS_NORMAL);
			user.setName(tenant.getTenantName());
			userService.insert(user);
			// 用户账号
			UserAuths userAuths = new UserAuths();
			userAuths.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode(), tenant.getTenantCode());
			userAuths.setUserId(user.getId());
			userAuths.setIdentifier(tenant.getTenantCode());
			userAuths.setIdentityType(IdentityType.PASSWORD.getValue());
			userAuths.setCredential(userService.encodeCredential(CommonConstant.DEFAULT_PASSWORD));
			userAuthsService.insert(userAuths);
			// 绑定角色
			Role role = new Role();
			role.setRoleCode(SecurityConstant.ROLE_TENANT_ADMIN);
			role = roleService.findByRoleCode(role);
			UserRole userRole = new UserRole();
			userRole.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode(), tenant.getTenantCode());
			userRole.setUserId(user.getId());
			userRole.setRoleId(role.getId());
			userRoleService.insert(userRole);
		}
		return super.update(tenant);
	}

	/**
	 * 删除.
	 *
	 * @param tenant tenant
	 * @return Tenant
	 * @author cooingandwooing
	 * @date 2019/05/26 10:28
	 */
	@Transactional
	@CacheEvict(value = "tenant", key = "#tenant.tenantCode")
	@Override
	public int delete(Tenant tenant) {
		// 删除菜单
		Menu menu = new Menu();
		menu.setTenantCode(tenant.getTenantCode());
		menuService.deleteByTenantCode(menu);
		// TODO 删除用户

		// TODO 删除权限
		return super.delete(tenant);
	}

	/**
	 * 删除.
	 *
	 * @param ids ids
	 * @return Tenant
	 * @author cooingandwooing
	 * @date 2019/05/26 10:37
	 */
	@Transactional
	@CacheEvict(value = "tenant", allEntries = true)
	@Override
	public int deleteAll(Long[] ids) {
		return super.deleteAll(ids);
	}
}
