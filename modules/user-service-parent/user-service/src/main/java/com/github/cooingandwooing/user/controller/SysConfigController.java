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

package com.github.cooingandwooing.user.controller;

import com.github.cooingandwooing.common.core.dto.SysConfigDto;
import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.properties.SysProperties;
import com.github.cooingandwooing.common.core.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统配置controller.
 *
 * @author cooingandwooing
 * @date 2019-02-28 17:29
 */
@AllArgsConstructor
@Api("系统配置信息管理")
@RestController
@RequestMapping("/v1/sysConfig")
public class SysConfigController extends BaseController {

	private final SysProperties sysProperties;

	/**
	 * 获取系统配置.
	 *
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/2/28 17:31
	 */
	@GetMapping
	@ApiOperation(value = "获取系统配置", notes = "获取系统配置")
	public ResponseBean<SysConfigDto> getSysConfig() {
		SysConfigDto sysConfigDto = new SysConfigDto();
		BeanUtils.copyProperties(sysProperties, sysConfigDto);
		return new ResponseBean<>(sysConfigDto);
	}
}
