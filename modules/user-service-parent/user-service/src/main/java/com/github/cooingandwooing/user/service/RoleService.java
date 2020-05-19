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
import com.github.cooingandwooing.user.api.module.Role;
import com.github.cooingandwooing.user.mapper.RoleMapper;
import com.github.cooingandwooing.user.mapper.RoleMenuMapper;
import com.github.cooingandwooing.user.mapper.UserRoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色service
 *
 * @author cooingandwooing
 * @date 2018/8/26 14:16
 */
@AllArgsConstructor
@Service
public class RoleService extends CrudService<RoleMapper, Role> {

    private final RoleMenuMapper roleMenuMapper;

    private final UserRoleMapper userRoleMapper;

    /**
     * 查询所有角色
     *
     * @param role role
     * @return List
     * @author cooingandwooing
     * @date 2019/05/15 23:32
     */
    @Override
    @Cacheable(value = "role#" + CommonConstant.CACHE_EXPIRE, key = "#role.applicationCode")
    public List<Role> findAllList(Role role) {
        return super.findAllList(role);
    }

    /**
     * 根据角色code查询
     *
     * @param role role
     * @return Role
     * @author cooingandwooing
     * @date 2019/09/21 12:07:47
     */
    @Cacheable(value = "role#" + CommonConstant.CACHE_EXPIRE, key = "#role.roleCode")
    public Role findByRoleCode(Role role) {
        return this.dao.findByRoleCode(role);
    }

    /**
     * 新增
     *
     * @param role
     * @return int
     */
    @Override
    @Transactional
    @CacheEvict(value = "role", key = "#role.roleCode")
    public int insert(Role role) {
        return super.insert(role);
    }

    /**
     * 更新
     *
     * @param role role
     * @return int
     */
    @Override
    @Transactional
    @CacheEvict(value = "role", key = "#role.roleCode")
    public int update(Role role) {
        return super.update(role);
    }

    /**
     * 删除
     *
     * @param role role
     * @return int
     */
    @Override
    @Transactional
    @CacheEvict(value = "role", key = "#role.roleCode")
    public int delete(Role role) {
        // 删除角色菜单关系
        roleMenuMapper.deleteByRoleId(role.getId());
        // 删除用户角色关系
        userRoleMapper.deleteByRoleId(role.getId());
        return super.delete(role);
    }
}
