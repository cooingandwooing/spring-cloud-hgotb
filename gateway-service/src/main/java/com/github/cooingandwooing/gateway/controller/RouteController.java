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

package com.github.cooingandwooing.gateway.controller;

import javax.validation.Valid;

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.exceptions.CommonException;
import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.utils.PageUtil;
import com.github.cooingandwooing.common.core.web.BaseController;
import com.github.cooingandwooing.gateway.module.Route;
import com.github.cooingandwooing.gateway.service.RouteService;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 路由controller.
 * TODO：增加security认证
 *
 * @author gaoxiaofeng
 * @date 2019/4/2 15:03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/route/v1/route")
public class RouteController extends BaseController {

	private final RouteService routeService;

	/**
	 * 根据id获取路由.
	 *
	 * @param id id
	 * @return Route
	 * @author tangyi
	 * @date 2019/4/2 15:09
	 */
	@GetMapping("/{id}")
	public Route get(@PathVariable Long id) {
		try {
			Route route = new Route();
			route.setId(id);
			return routeService.get(route);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(e.getMessage());
		}
	}

	/**
	 * 路由分页查询.
	 *
	 * @param pageNum  pageNum
	 * @param pageSize pageSize
	 * @param sort     sort
	 * @param order    order
	 * @param route    route
	 * @return PageInfo
	 * @author tangyi
	 * @date 2019/4/2 15:09
	 */
	@GetMapping("routeList")
	public PageInfo<Route> userList(@RequestParam(value = CommonConstant.PAGE_NUM, required = false, defaultValue = CommonConstant.PAGE_NUM_DEFAULT) String pageNum,
		@RequestParam(value = CommonConstant.PAGE_SIZE, required = false, defaultValue = CommonConstant.PAGE_SIZE_DEFAULT) String pageSize,
		@RequestParam(value = CommonConstant.SORT, required = false, defaultValue = CommonConstant.PAGE_SORT_DEFAULT) String sort,
		@RequestParam(value = CommonConstant.ORDER, required = false, defaultValue = CommonConstant.PAGE_ORDER_DEFAULT) String order,
		Route route) {
		return routeService.findPage(PageUtil.pageInfo(pageNum, pageSize, sort, order), route);
	}

	/**
	 * 修改路由.
	 *
	 * @param route route
	 * @return ResponseBean
	 * @author tangyi
	 * @date 2019/4/2 15:09
	 */
	@PutMapping
	public ResponseBean<Boolean> updateRoute(@RequestBody @Valid Route route) {
		try {
			// 更新路由
			return new ResponseBean<>(routeService.update(route) > 0);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(e.getMessage());
		}
	}

	/**
	 * 创建路由.
	 *
	 * @param route route
	 * @return ResponseBean
	 * @author tangyi
	 * @date 2019/4/2 15:09
	 */
	@PostMapping
	public ResponseBean<Boolean> add(@RequestBody @Valid Route route) {
		try {
			// 新增路由
			return new ResponseBean<>(routeService.insert(route) > 0);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(e.getMessage());
		}
	}

	/**
	 * 根据id删除路由.
	 *
	 * @param id id
	 * @return ResponseBean
	 * @author tangyi
	 * @date 2019/4/2 15:09
	 */
	@DeleteMapping("/{id}")
	public ResponseBean<Boolean> delete(@PathVariable Long id) {
		try {
			return new ResponseBean<>(routeService.delete(id) > 0);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(e.getMessage());
		}
	}

	/**
	 * 批量删除.
	 *
	 * @param ids ids
	 * @return ResponseBean
	 * @author tangyi
	 * @date 2019/4/2 15:09
	 */
	@PostMapping("deleteAll")
	public ResponseBean<Boolean> deleteAll(@RequestBody Long[] ids) {
		boolean success = false;
		try {
			/* isNotEmpty -> !isEmpty*/
			if (!ArrayUtils.isEmpty(ids)) {
				success = routeService.deleteAll(ids) > 0;
			}
			return new ResponseBean<>(success);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(e.getMessage());
		}
	}


	/**
	 * 刷新路由.
	 *
	 * @return ResponseBean
	 * @author gaoxiaofeng
	 * @date 2019/04/07 12:32
	 */
	@GetMapping("refresh")
	public ResponseBean<Boolean> refresh() {
		try {
			return new ResponseBean<>(routeService.refresh());
		}
		catch (Exception e) {
			log.error(e.getMessage());
			throw new CommonException(e.getMessage());
		}
	}
}
