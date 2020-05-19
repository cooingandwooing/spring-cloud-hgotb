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

package com.github.cooingandwooing.gateway.filters;

import cn.hutool.core.util.StrUtil;
import com.github.cooingandwooing.gateway.constants.GatewayConstant;
import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.properties.SysProperties;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 解密过滤器
 * 对外密码字段的名称是credential，在这里解密，转换成password
 *
 * 用到springcloud 的gateway作为网关做统一的请求转发，需求在gateway模块处理请求过来的参数进行解密然后再转发到后续模块，
 * 后续模块处理完毕再转回getway处理加密然后返回前端的过程
 * 解密：在gateway中加decodeFilter，在run方法中处理，处理解密主要针对get方式的url参数以及类似post请求的requestBody的参数进行处理。
 *
 * 加密：在gateway中加encodeFilter，在run方法中处理加密操作
 * @author gaoxiaofeng
 * @date 2019/3/18 11:30
 */
@Slf4j
@AllArgsConstructor
@Configuration
@ConditionalOnProperty(value = "sys.key")
public class DecodePasswordFilter implements GlobalFilter, Ordered {

    private static final String CREDENTIAL = "credential";

    private static final String PASSWORD = "password";

    private final SysProperties sysProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 当前请求
        ServerHttpRequest request = exchange.getRequest();
        // 请求的URI
        URI uri = request.getURI();
        // 获取token的请求
        if (HttpMethod.POST.matches(request.getMethodValue()) && StrUtil.containsAnyIgnoreCase(uri.getPath(), GatewayConstant.OAUTH_TOKEN_URL, GatewayConstant.REGISTER,
                GatewayConstant.MOBILE_TOKEN_URL)) {
            String grantType = request.getQueryParams().getFirst(GatewayConstant.GRANT_TYPE);
            // 授权类型为密码模式则解密
            if (CommonConstant.GRANT_TYPE_PASSWORD.equals(grantType) || StrUtil.containsAnyIgnoreCase(uri.getPath(), GatewayConstant.REGISTER)) {
                String credential = request.getQueryParams().getFirst(CREDENTIAL);
                if (StringUtils.isNotBlank(credential)) {
                    try {
                        // 开始解密
                        credential = SysUtil.decryptAES(credential, sysProperties.getKey());
                        credential = credential.trim();
                    } catch (Exception e) {
                        log.error("credential decrypt fail:{}", credential);
                    }
                    URI newUri = UriComponentsBuilder.fromUri(uri)
                            // 替换password字段
                            .replaceQueryParam(PASSWORD, credential)
                            // 替换credential字段
                            .replaceQueryParam(CREDENTIAL, credential)
                            .build(true).toUri();
                    request = request.mutate().uri(newUri).build();
                    return chain.filter(exchange.mutate().request(request).build());
                }
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
