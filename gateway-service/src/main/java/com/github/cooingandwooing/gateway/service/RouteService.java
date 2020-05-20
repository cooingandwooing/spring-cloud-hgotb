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

package com.github.cooingandwooing.gateway.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.exceptions.CommonException;
import com.github.cooingandwooing.common.core.service.CrudService;
import com.github.cooingandwooing.common.core.utils.JsonMapper;
import com.github.cooingandwooing.gateway.constants.GatewayConstant;
import com.github.cooingandwooing.gateway.mapper.RouteMapper;
import com.github.cooingandwooing.gateway.module.Route;
import com.github.cooingandwooing.gateway.vo.RouteFilterVo;
import com.github.cooingandwooing.gateway.vo.RoutePredicateVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 路由service.
 *
 * @author gaoxiaofeng
 * @date 2019/4/2 15:01
 */
@Slf4j
@AllArgsConstructor
@Service
public class RouteService extends CrudService<RouteMapper, Route> {

	private final DynamicRouteService dynamicRouteService;

	private final RedisTemplate redisTemplate;

	/**
	 * 新增路由.
	 *
	 * @param route route
	 * @return int
	 */
	@Override
	public int insert(Route route) {
		int update;
		if (StringUtils.isBlank(route.getRouteId())) {
			throw new CommonException("服务ID不能为空！");
		}
		// 校验服务路由是否存在
		Route condition = new Route();
		condition.setRouteId(route.getRouteId());
		List<Route> routes = this.findList(condition);
		if (CollectionUtils.isNotEmpty(routes)) {
			throw new CommonException("该服务的路由已存在！");
		}
		route.setCommonValue("", GatewayConstant.SYS_CODE, GatewayConstant.DEFAULT_TENANT_CODE);
		update = this.dao.insert(route);
		if (update > 0) {
			dynamicRouteService.add(routeDefinition(route));
		}
		return update;
	}

	/**
	 * 更新路由.
	 *
	 * @param route route
	 * @return int
	 */
	@Override
	public int update(Route route) {
		int update;
		if (StringUtils.isBlank(route.getRouteId())) {
			throw new CommonException("服务ID不能为空！");
		}
		route.setNewRecord(false);
		route.setCommonValue("", GatewayConstant.SYS_CODE, GatewayConstant.DEFAULT_TENANT_CODE);
		update = this.dao.update(route);
		if (update > 0) {
			dynamicRouteService.update(routeDefinition(route));
		}
		return update;
	}

	/**
	 * 删除路由.
	 *
	 * @param id id
	 * @return Mono
	 */
	@Transactional
	public int delete(Long id) {
		Route route = new Route();
		route.setId(id);
		route.setNewRecord(false);
		route.setCommonValue("", GatewayConstant.SYS_CODE, GatewayConstant.DEFAULT_TENANT_CODE);
		int update = this.dao.delete(route);
		dynamicRouteService.delete(id);
		return update;
	}

	/**
	 * 刷新路由.
	 *
	 * @return boolean
	 */
	public boolean refresh() {
		Route init = new Route();
		init.setStatus(CommonConstant.DEL_FLAG_NORMAL.toString());
		List<Route> routes = this.findList(init);
		if (CollectionUtils.isNotEmpty(routes)) {
			log.info("加载{}条路由记录", routes.size());
			for (Route route : routes) {
				dynamicRouteService.update(routeDefinition(route));
			}
			// 存入Redis for RegistrySwaggerResourcesProvider swagger-ui显示
			redisTemplate.opsForValue().set(CommonConstant.ROUTE_KEY, JsonMapper.getInstance().toJson(routes));
		}
		return true;
	}

	/**
	 * 初始化RouteDefinition.
	 *
	 * @param route route
	 * @return RouteDefinition 路由
	 * @author gaoxiaofeng
	 * @date 2019/04/02 18:50
	 */
	private RouteDefinition routeDefinition(Route route) {
		RouteDefinition routeDefinition = new RouteDefinition();
		// id
		routeDefinition.setId(route.getRouteId());

		// predicates
		if (StringUtils.isNotBlank(route.getPredicates())) {
			routeDefinition.setPredicates(predicateDefinitions(route));
		}

		// filters
		if (StringUtils.isNotBlank(route.getFilters())) {
			routeDefinition.setFilters(filterDefinitions(route));
		}
		// uri
		routeDefinition.setUri(URI.create(route.getUri()));
		return routeDefinition;
	}

	/**
	 * @param route route
	 * @return List 断言
	 * @author gaoxiaofeng
	 * @date 2019/04/02 21:28
	 */
	private List<PredicateDefinition> predicateDefinitions(Route route) {
		List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
		try {
			List<RoutePredicateVo> routePredicateVoList = JsonMapper.getInstance().fromJson(route.getPredicates(),
				JsonMapper.getInstance().createCollectionType(ArrayList.class, RoutePredicateVo.class));
			if (CollectionUtils.isNotEmpty(routePredicateVoList)) {
				for (RoutePredicateVo routePredicateVo : routePredicateVoList) {
					PredicateDefinition predicate = new PredicateDefinition();
					predicate.setArgs(routePredicateVo.getArgs());
					predicate.setName(routePredicateVo.getName());
					predicateDefinitions.add(predicate);
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return predicateDefinitions;
	}

	/**
	 * @param route route
	 * @return List 过滤器
	 * @author gaoxiaofeng
	 * @date 2019/04/02 21:29
	 */
	private List<FilterDefinition> filterDefinitions(Route route) {
		List<FilterDefinition> filterDefinitions = new ArrayList<>();
		try {
			JavaType javaType = JsonMapper.getInstance().createCollectionType(ArrayList.class, RouteFilterVo.class);
			List<RouteFilterVo> gatewayFilterDefinitions = JsonMapper.getInstance()
				.fromJson(route.getFilters(), javaType);
			if (CollectionUtils.isNotEmpty(gatewayFilterDefinitions)) {
				for (RouteFilterVo gatewayFilterDefinition : gatewayFilterDefinitions) {
					FilterDefinition filterDefinition = new FilterDefinition();
					filterDefinition.setName(gatewayFilterDefinition.getName());
					filterDefinition.setArgs(gatewayFilterDefinition.getArgs());
					filterDefinitions.add(filterDefinition);
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return filterDefinitions;
	}
}
