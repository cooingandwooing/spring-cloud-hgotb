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

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由信息
 * 1.创建路由模型
 * @author gaoxiaofeng
 * @date 2019/3/27 11:06
 */
@Data
public class RouteVo {

    /**
     * 路由的Id
     */
    private String routeId;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 路由断言集合配置
     */
    private List<RoutePredicateVo> predicates = new ArrayList<>();

    /**
     * 路由过滤器集合配置
     */
    private List<RouteFilterVo> filters = new ArrayList<>();

    /**
     * 路由规则转发的目标uri
     */
    private String uri;

    /**
     * 路由执行的顺序
     */
    private int order = 0;

    /**
     * 排序
     */
    private String sort;

    /**
     * 启用禁用
     */
    private String status;

}
