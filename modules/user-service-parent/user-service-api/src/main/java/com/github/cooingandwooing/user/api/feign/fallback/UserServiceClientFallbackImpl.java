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

package com.github.cooingandwooing.user.api.feign.fallback;

import java.util.ArrayList;
import java.util.List;

import com.github.cooingandwooing.common.core.model.Log;
import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.vo.AttachmentVo;
import com.github.cooingandwooing.common.core.vo.DeptVo;
import com.github.cooingandwooing.common.core.vo.UserVo;
import com.github.cooingandwooing.user.api.dto.UserDto;
import com.github.cooingandwooing.user.api.dto.UserInfoDto;
import com.github.cooingandwooing.user.api.feign.UserServiceClient;
import com.github.cooingandwooing.user.api.module.Menu;
import com.github.cooingandwooing.user.api.module.Tenant;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志断路器实现.
 *
 * @author cooingandwooing
 * @date 2019/3/23 23:39
 */
@Slf4j
@Component
public class UserServiceClientFallbackImpl implements UserServiceClient {

	private Throwable throwable;

	/**
	 * 根据用户名查询用户信息.
	 *
	 * @param identifier identifier
	 * @param tenantCode 租户标识
	 * @param tenantCode 租户标识
	 * @return ResponseBean
	 */
	@GetMapping("/v1/user/findUserByIdentifier/{identifier}")
	@Override
	public ResponseBean<UserVo> findUserByIdentifier(String identifier, String tenantCode) {
		log.error("feign 查询用户信息失败:{}, {}, {}", tenantCode, identifier, throwable);
		return null;
	}

	/**
	 * 根据用户名查询用户信息.
	 *
	 * @param identifier   identifier
	 * @param identityType identityType
	 * @param tenantCode   租户标识
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<UserVo> findUserByIdentifier(String identifier, Integer identityType, String tenantCode) {
		log.error("feign 查询用户信息失败:{}, {}, {}, {}", tenantCode, identityType, identifier, throwable);
		return null;
	}

	/**
	 * 查询当前登录的用户信息.
	 *
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<UserInfoDto> info() {
		log.error("feign 查询用户信息失败:{},{}", throwable);
		return null;
	}

	/**
	 * 根据用户ID批量查询用户信息.
	 *
	 * @param ids ids
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<List<UserVo>> findUserById(@RequestBody Long[] ids) {
		log.error("调用{}异常:{},{}", "findById", ids, throwable);
		return null;
	}

	/**
	 * 查询用户数量.
	 *
	 * @param userVo userVo
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<Integer> findUserCount(UserVo userVo) {
		log.error("调用{}异常:{},{}", "findUserCount", userVo, throwable);
		return new ResponseBean<>(0);
	}

	/**
	 * 根据部门ID批量查询部门信息.
	 *
	 * @param ids ids
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<List<DeptVo>> findDeptById(@RequestBody Long[] ids) {
		log.error("调用{}异常:{},{}", "findById", ids, throwable);
		return null;
	}

	/**
	 * 根据附件ID删除附件.
	 *
	 * @param id id
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<Boolean> deleteAttachment(Long id) {
		log.error("调用{}异常:{},{}", "delete", id, throwable);
		return new ResponseBean<>(Boolean.FALSE);
	}

	/**
	 * 根据附件ID批量查询附件信息.
	 *
	 * @param ids ids
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<List<AttachmentVo>> findAttachmentById(Long[] ids) {
		log.error("调用{}异常:{},{}", "findById", ids, throwable);
		return new ResponseBean<>(new ArrayList<>());
	}

	/**
	 * 保存日志.
	 *
	 * @param logInfo logInfo
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<Boolean> saveLog(Log logInfo) {
		log.error("feign 插入日志失败,{}", throwable);
		return null;
	}

	/**
	 * 根据角色查找菜单.
	 *
	 * @param tenantCode 租户标识
	 * @param role       角色
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<List<Menu>> findMenuByRole(String role, String tenantCode) {
		log.error("feign 获取角色菜单失败, {}, {}", tenantCode, throwable);
		return new ResponseBean<>(new ArrayList<>());
	}

	/**
	 * 查询所有菜单.
	 *
	 * @param tenantCode 租户标识
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<List<Menu>> findAllMenu(String tenantCode) {
		log.error("feign 获取所有菜单失败, {}, {}", tenantCode, throwable);
		return new ResponseBean<>(new ArrayList<>());
	}

	/**
	 * 根据租户标识查询租户详细信息.
	 *
	 * @param tenantCode 租户标识
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<Tenant> findTenantByTenantCode(String tenantCode) {
		log.error("feign 获取租户详细信息失败, {}, {}", tenantCode, throwable);
		return null;
	}

	/**
	 * 根据社交账号获取用户详细信息.
	 *
	 * @param social     social
	 * @param tenantCode 租户标识
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<UserVo> findUserBySocial(String social, String tenantCode) {
		log.error("feign 根据社交账号获取用户详细信息失败, {}, {}, {}", social, tenantCode, throwable);
		return null;
	}

	/**
	 * 注册用户.
	 *
	 * @param userDto userDto
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<Boolean> registerUser(UserDto userDto) {
		log.error("feign 注册用户失败, {}, {}, {}", userDto.getIdentityType(), userDto.getIdentifier(), throwable);
		return null;
	}

	/**
	 * 更新用户.
	 *
	 * @param userDto userDto
	 * @return ResponseBean
	 */
	@Override
	public ResponseBean<Boolean> updateUser(UserDto userDto) {
		log.error("feign 更新用户失败, {}, {}, {}", userDto.getIdentityType(), userDto.getIdentifier(), throwable);
		return null;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
