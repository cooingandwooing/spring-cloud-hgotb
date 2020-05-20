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

package com.github.cooingandwooing.gateway.vo;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

/**
 * 过滤器信息.
 * 2.创建过滤器模型.
 * @author gaoxiaofeng
 * @date 2019/3/27 11:07
 */
@Data
public class RouteFilterVo {

	/**
	 * Filter Name.
	 */
	private String name;

	/**
	 * 对应的路由规则.
	 */
	private Map<String, String> args = new LinkedHashMap<>();
}
