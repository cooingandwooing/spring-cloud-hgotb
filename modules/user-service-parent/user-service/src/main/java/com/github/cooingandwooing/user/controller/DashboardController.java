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

import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import com.github.cooingandwooing.common.core.vo.UserVo;
import com.github.cooingandwooing.common.core.web.BaseController;
import com.github.cooingandwooing.user.api.dto.DashboardDto;
import com.github.cooingandwooing.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台首页数据展示.
 *
 * @author cooingandwooing
 * @date 2019-03-01 13:54
 */
@AllArgsConstructor
@Api("后台首页数据展示")
@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController extends BaseController {

	//private final ExaminationServiceClient examinationService;

	private final UserService userService;

	/**
	 * 获取管控台首页数据.
	 *
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/3/1 13:55
	 */
	@GetMapping
	@ApiOperation(value = "后台首页数据展示", notes = "后台首页数据展示")
	public ResponseBean<DashboardDto> dashboard() {
		String tenantCode = SysUtil.getTenantCode();
		DashboardDto dashboardDto = new DashboardDto();
		// 查询用户数量
		UserVo userVo = new UserVo();
		userVo.setTenantCode(tenantCode);
		dashboardDto.setOnlineUserNumber(userService.userCount(userVo).toString());
		// 查询考试数量
		//ResponseBean<Integer> examinationCountResponseBean = examinationService.findExaminationCount(tenantCode);
		/*if (!ResponseUtil.isSuccess(examinationCountResponseBean))
			throw new ServiceException("查询考试数量失败: " + examinationCountResponseBean.getMsg());
		dashboardDto.setExaminationNumber(examinationCountResponseBean.getData().toString());
		// 查询参与人数
		ResponseBean<Integer> examUserCountResponseBean = examinationService.findExamUserCount(tenantCode);
		if (!ResponseUtil.isSuccess(examUserCountResponseBean))
			throw new ServiceException("查询参与人数失败: " + examUserCountResponseBean.getMsg());
		dashboardDto.setExamUserNumber(examUserCountResponseBean.getData().toString());*/
		return new ResponseBean<>(dashboardDto);
	}
}
