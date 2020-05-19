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

package com.github.cooingandwooing.common.security.core;

import com.github.cooingandwooing.common.security.constant.SecurityConstant;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * UserDetails封装
 *
 * @author gaoxiaofeng
 * @date 2019/3/17 14:37
 */
@Data
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = -6509897037222767090L;

    /**
     * 权限
     */
    private Set<GrantedAuthority> authorities;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
    private String username;

    /**
     * 启用禁用状态
     */
    private String status;

    /**
     * 租户标识
     */
    private String tenantCode;

    public UserDetailsImpl(String tenantCode, String username, String password, String status, Set<GrantedAuthority> authorities) {
        this.tenantCode = tenantCode;
        this.authorities = authorities;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return SecurityConstant.NORMAL.equals(this.status);
    }
}
