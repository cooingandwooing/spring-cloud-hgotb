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

package com.github.cooingandwooing.gateway.config;

import java.util.Collections;
import java.util.List;

import com.github.cooingandwooing.gateway.handler.GatewayExceptionHandler;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

/**
 * 网关异常处理.
 *
 * @author gaoxiaofeng
 * @date 2019/3/18 20:50
 */
@Configuration
public class GatewayExceptionConfig {

	/**
	 * 自定义异常处理.
	 */
	@Primary
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer) {
		GatewayExceptionHandler gatewayExceptionHandler = new GatewayExceptionHandler();
		gatewayExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
		gatewayExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
		gatewayExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
		return gatewayExceptionHandler;
	}
}
