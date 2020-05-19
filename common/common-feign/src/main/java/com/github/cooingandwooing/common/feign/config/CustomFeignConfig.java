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

package com.github.cooingandwooing.common.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 服务间调用携带Authorization、Tenant-Code请求头
 *
 * @author gaoxiaofeng
 * @date 2019-03-15 14:14
 */
@Configuration
public class CustomFeignConfig implements RequestInterceptor {

    private static final String TOKEN_HEADER = "authorization";

    private static final String TENANT_HEADER = "Tenant-Code";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = getHttpServletRequest();
        if (request != null) {
            requestTemplate.header(TOKEN_HEADER, getHeaders(request).get(TOKEN_HEADER));
            requestTemplate.header(TENANT_HEADER, getHeaders(request).get(TENANT_HEADER));
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            // hystrix隔离策略会导致RequestContextHolder.getRequestAttributes()返回null
            // 解决方案：http://www.itmuch.com/spring-cloud-sum/hystrix-threadlocal/
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
