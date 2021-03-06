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

import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 将HTTPS改为HTTP.
 * 网关进来的协议是HTTPS，其它服务一般部署在内网，没必要走HTTPS
 *
 * @author gaoxiaofeng
 * @date 2019/08/03 12:03
 */
@Component
public class HttpsToHttpFilter implements GlobalFilter, Ordered {

	private static final int HTTPS_TO_HTTP_FILTER_ORDER = 10099;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		URI originalUri = exchange.getRequest().getURI();
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpRequest.Builder mutate = request.mutate();
		String forwardedUri = request.getURI().toString();
		if (forwardedUri != null && forwardedUri.startsWith("https")) {
			try {
				URI mutatedUri = new URI("http",
					originalUri.getUserInfo(),
					originalUri.getHost(),
					originalUri.getPort(),
					originalUri.getPath(),
					originalUri.getQuery(),
					originalUri.getFragment());
				mutate.uri(mutatedUri);
			}
			catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage(), e);
			}
		}
		ServerHttpRequest build = mutate.build();
		return chain.filter(exchange.mutate().request(build).build());
	}

	/**
	 * LoadBalancerClientFilter的order是10100.
	 * 要在LoadBalancerClientFilter执行之前将HTTPS修改为HTTP，则这里的order设置为10099
	 *
	 * @return int
	 */
	@Override
	public int getOrder() {
		return HTTPS_TO_HTTP_FILTER_ORDER;
	}
}
