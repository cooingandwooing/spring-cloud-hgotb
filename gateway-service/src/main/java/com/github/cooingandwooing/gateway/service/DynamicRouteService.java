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

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 动态路由Service.
 *
 * @author gaoxiaofeng
 * @date 2019/3/27 10:59
 */
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

	private final RouteDefinitionWriter routeDefinitionWriter;

	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	public DynamicRouteService(RouteDefinitionWriter routeDefinitionWriter) {
		this.routeDefinitionWriter = routeDefinitionWriter;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	/**
	 * 增加路由.
	 *
	 * @param definition definition
	 * @return String
	 */
	public String add(RouteDefinition definition) {
		routeDefinitionWriter.save(Mono.just(definition)).subscribe();
		this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
		return "success";
	}

	/**
	 * 更新路由.
	 *
	 * @param definition definition
	 * @return String
	 */
	public String update(RouteDefinition definition) {
		try {
			this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
		}
		catch (Exception e) {
			return "update fail,not find route  routeId: " + definition.getId();
		}
		try {
			routeDefinitionWriter.save(Mono.just(definition)).subscribe();
			this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
			return "success";
		}
		catch (Exception e) {
			return "update route fail";
		}
	}

	/**
	 * 删除路由.
	 *
	 * @param id id
	 * @return Mono
	 */
	public Mono<ResponseEntity<Object>> delete(Long id) {
		return this.routeDefinitionWriter.delete(Mono.just(String.valueOf(id)))
			.then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
			.onErrorResume(t -> t instanceof NotFoundException, t -> Mono.just(ResponseEntity.notFound().build()));
	}
}
