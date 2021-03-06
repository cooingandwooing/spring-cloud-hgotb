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

package com.github.cooingandwooing.auth.model;

import java.util.Collection;
import java.util.Objects;

import com.github.cooingandwooing.common.core.enums.LoginType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 用户信息.
 *
 * @author gaoxiaofeng
 * @date 2019/5/28 21:13
 */
public class CustomUserDetails extends User {

	private static final long serialVersionUID = 1L;

	/**
	 * 租户标识.
	 */
	private String tenantCode;

	/**
	 * 开始授权时间.
	 */
	private long start;

	/**
	 * 登录类型.
	 */
	private LoginType loginType;

	/**
	 * 构造方法.
	 *
	 * @param username    username
	 * @param password    password
	 * @param enabled     enabled
	 * @param authorities authorities
	 * @param tenantCode  tenantCode
	 * @param start       start
	 * @param loginType   loginType
	 */
	public CustomUserDetails(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, String tenantCode, long start, LoginType loginType) {
		super(username, password, enabled, true, true, true, authorities);
		this.tenantCode = tenantCode;
		this.start = start;
		this.loginType = loginType;
	}

	static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	LoginType getLoginType() {
		return loginType;
	}

	void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CustomUserDetails)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		CustomUserDetails that = (CustomUserDetails) o;
		return getStart() == that.getStart() &&
			Objects.equals(getTenantCode(), that.getTenantCode()) &&
			getLoginType() == that.getLoginType();
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getTenantCode(), getStart(), getLoginType());
	}

	@Override
	public String toString() {
		return "CustomUserDetails{" +
			"tenantCode='" + tenantCode + '\'' +
			", start=" + start +
			", loginType=" + loginType +
			"} " + super.toString();
	}
}
