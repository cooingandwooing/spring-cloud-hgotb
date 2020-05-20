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

package com.github.cooingandwooing.common.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.cooingandwooing.common.security.constant.SecurityConstant;
import com.github.cooingandwooing.common.security.tenant.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 获取请求头里的租户code.
 *
 * @author gaoxiaofeng
 * @date 2019/5/28 22:53
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantTokenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 获取请求头里的TENANT_CODE
		String tenantCode = request.getHeader(SecurityConstant.TENANT_CODE_HEADER);
		// 没有携带tenantCode则采用默认的tenantCode
		if (tenantCode == null) {
			tenantCode = SecurityConstant.DEFAULT_TENANT_CODE;
		}
		TenantContextHolder.setTenantCode(tenantCode);
		filterChain.doFilter(request, response);
		TenantContextHolder.clear();
	}
}
