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

package com.github.cooingandwooing.common.security.utils;

import java.io.IOException;
import java.security.Principal;

import com.github.cooingandwooing.common.security.core.UserDetailsImpl;
import org.bouncycastle.util.encoders.Base64;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 安全工具类.
 *
 * @author gaoxiaofeng
 * @date 2019/3/17 11:44
 */
public class SecurityUtil {

	protected SecurityUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取当前用户的租户标识.
	 *
	 * @return String
	 * @author gaoxiaofeng
	 * @date 2019/05/25 14:19
	 */
	public static String getCurrentUserTenantCode() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal instanceof UserDetails ? ((UserDetailsImpl) principal).getTenantCode() : "";
	}

	/**
	 * 获取当前登录的用户名.
	 *
	 * @return String
	 * @author gaoxiaofeng
	 * @date 2019/03/17 11:46
	 */
	public static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		if (principal instanceof Principal) {
			return ((Principal) principal).getName();
		}
		return String.valueOf(principal);
	}

	/**
	 * 获取当前用户的授权信息.
	 *
	 * @return Authentication
	 * @author gaoxiaofeng
	 * @date 2019/03/17 19:18
	 */
	public static Authentication getCurrentAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取当前登录用户的授权信息.
	 *
	 * @return Object
	 * @author gaoxiaofeng
	 * @date 2019/03/17 11:48
	 */
	public static Object getCurrentPrincipal() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	/**
	 * 从header 请求中的clientId/clientsecect.
	 *
	 * @param header header中的参数
	 * @throws RuntimeException if the Basic header is not present or is not valid
	 *                          Base64
	 */
	public static String[] extractAndDecodeHeader(String header) throws IOException {
		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new RuntimeException("Failed to decode basic authentication token");
		}
		String token = new String(decoded, "UTF8");
		int delim = token.indexOf(":");
		if (delim == -1) {
			throw new RuntimeException("Invalid basic authentication token");
		}
		return new String[] {token.substring(0, delim), token.substring(delim + 1)};
	}
}
