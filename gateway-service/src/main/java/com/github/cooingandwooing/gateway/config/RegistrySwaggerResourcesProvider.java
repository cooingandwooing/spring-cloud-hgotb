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

import java.util.ArrayList;
import java.util.List;

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.utils.JsonMapper;
import com.github.cooingandwooing.gateway.module.Route;
import com.github.cooingandwooing.gateway.service.RouteService;
import com.github.cooingandwooing.gateway.vo.RoutePredicateVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
/**
 * Swagger聚合文档 聚合各个服务的swagger接口.
 * 目前问题：结合动态更新路由之后，GatewayProperties获取不到新的路由列表，导致swagger-ui显示不了
 * 解决办法：从Redis里读取路由数据
 *
 * @author gaoxiaofeng
 * @date 2019/3/26 15:39
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class RegistrySwaggerResourcesProvider implements SwaggerResourcesProvider {
	/**
	 * swagger2默认的url后缀.
	 */
	private static final String API_URI = "/v2/api-docs";
	/**
	 * 网关路由.
	 */
	private final RouteLocator routeLocator;

	private final SwaggerProviderConfig swaggerProviderConfig;

	private final RedisTemplate redisTemplate;

	private final RouteService routeService;

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		List<String> routes = new ArrayList<>();
		// 取出gateway的route
		routeLocator.getRoutes().subscribe(route -> {
			// 根据文档提供者过滤
			if (swaggerProviderConfig.getProviders().contains(route.getId())) {
				routes.add(route.getId());
			}
		});
		// 结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
		List<Route> routeList = this.routeList();
		if (CollectionUtils.isNotEmpty(routeList)) {
			for (Route route : routeList) {
				if (routes.contains(route.getRouteId())) {
					try {
						List<RoutePredicateVo> routePredicateVoList = JsonMapper.getInstance()
							.fromJson(route.getPredicates(), JsonMapper.getInstance()
								.createCollectionType(ArrayList.class, RoutePredicateVo.class));
						if (CollectionUtils.isNotEmpty(routePredicateVoList)) {
							String genKey;
							RoutePredicateVo routePredicateVo = routePredicateVoList.stream()
								.filter(routePredicate -> routePredicate.getArgs()
									.containsKey(NameUtils.GENERATED_NAME_PREFIX + "0")).findFirst().orElse(null);
							if (routePredicateVo != null) {
								genKey = routePredicateVo.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
									.replace("/**", API_URI);
								resources.add(swaggerResource(route.getRouteId(), genKey));
							}
						}
					}
					catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
		return resources;
	}

	/**
	 * 获取路由列表.
	 *
	 * @return List<Route/>
	 */
	private List<Route> routeList() {
		List<Route> routeList;
		// 结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
		Object object = redisTemplate.opsForValue().get(CommonConstant.ROUTE_KEY);
		if (object == null) {
			// 放入缓存
			routeList = routeService.findList(new Route());
			if (CollectionUtils.isNotEmpty(routeList)) {
				redisTemplate.opsForValue().set(CommonConstant.ROUTE_KEY, JsonMapper.getInstance().toJson(routeList));
			}
		}
		else {
			routeList = JsonMapper.getInstance().fromJson(object.toString(), JsonMapper.getInstance()
				.createCollectionType(ArrayList.class, Route.class));
		}
		return routeList;
	}

	/**
	 * 服务信息.
	 *
	 * @param name     name
	 * @param location location
	 * @return SwaggerResource
	 */
	private SwaggerResource swaggerResource(String name, String location) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}
}
