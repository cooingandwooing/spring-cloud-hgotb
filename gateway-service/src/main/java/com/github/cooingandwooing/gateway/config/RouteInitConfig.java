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

import javax.annotation.PostConstruct;

import com.github.cooingandwooing.gateway.service.RouteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;

/**
 * 初始化的时候加载路由数据.
 *
 * @author gaoxiaofeng
 * @date 2019/4/2 14:40
 */
@Slf4j
@AllArgsConstructor
@Configuration
public class RouteInitConfig {

	private final RouteService routeService;

	@PostConstruct
	public void initRoute() {
		routeService.refresh();
	}
}
