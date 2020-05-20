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

import javax.validation.Valid;

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.model.Log;
import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.utils.PageUtil;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import com.github.cooingandwooing.common.core.web.BaseController;
import com.github.cooingandwooing.common.security.constant.SecurityConstant;
import com.github.cooingandwooing.user.service.LogService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志controller.
 *
 * @author cooingandwooing
 * @date 2018/10/31 20:48
 */
@Slf4j
@AllArgsConstructor
@Api("日志信息管理")
@RestController
@RequestMapping("/v1/log")
public class LogController extends BaseController {

	private final LogService logService;

	/**
	 * 根据id获取日志.
	 *
	 * @param id id
	 * @return Log
	 * @author cooingandwooing
	 * @date 2018/9/14 18:20
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "获取日志信息", notes = "根据日志id获取日志详细信息")
	@ApiImplicitParam(name = "id", value = "日志ID", required = true, dataType = "Long", paramType = "path")
	public Log log(@PathVariable Long id) {
		try {
			return logService.get(id);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new Log();
	}

	/**
	 * 日志分页查询.
	 *
	 * @param pageNum  pageNum
	 * @param pageSize pageSize
	 * @param sort     sort
	 * @param order    order
	 * @param log      log
	 * @return PageInfo
	 * @author cooingandwooing
	 * @date 2018/10/24 0024 22:13
	 */
	@GetMapping("logList")
	@ApiOperation("获取日志列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = CommonConstant.PAGE_NUM, value = "分页页码", defaultValue = CommonConstant.PAGE_NUM_DEFAULT, dataType = "String"),
		@ApiImplicitParam(name = CommonConstant.PAGE_SIZE, value = "分页大小", defaultValue = CommonConstant.PAGE_SIZE_DEFAULT, dataType = "String"),
		@ApiImplicitParam(name = CommonConstant.SORT, value = "排序字段", defaultValue = CommonConstant.PAGE_SORT_DEFAULT, dataType = "String"),
		@ApiImplicitParam(name = CommonConstant.ORDER, value = "排序方向", defaultValue = CommonConstant.PAGE_ORDER_DEFAULT, dataType = "String"),
		@ApiImplicitParam(name = "log", value = "日志信息", dataType = "Log")
	})
	public PageInfo<Log> userList(@RequestParam(value = CommonConstant.PAGE_NUM, required = false, defaultValue = CommonConstant.PAGE_NUM_DEFAULT) String pageNum,
		@RequestParam(value = CommonConstant.PAGE_SIZE, required = false, defaultValue = CommonConstant.PAGE_SIZE_DEFAULT) String pageSize,
		@RequestParam(value = CommonConstant.SORT, required = false, defaultValue = CommonConstant.PAGE_SORT_DEFAULT) String sort,
		@RequestParam(value = CommonConstant.ORDER, required = false, defaultValue = CommonConstant.PAGE_ORDER_DEFAULT) String order,
		Log log) {
		log.setTenantCode(SysUtil.getTenantCode());
		return logService.findPage(PageUtil.pageInfo(pageNum, pageSize, sort, order), log);
	}

	/**
	 * 新增日志.
	 *
	 * @param log log
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/03/27 23:14
	 */
	@PostMapping
	@ApiOperation(value = "新增日志", notes = "新增日志")
	@ApiImplicitParam(name = "log", value = "日志实体Log", required = true, dataType = "Log")
	public ResponseBean<Boolean> addLog(@RequestBody @Valid Log log) {
		if (log.getId() != null) {
			log.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode(), SysUtil.getTenantCode());
		}
		if (true) {
			return null;
		}
		// 保存日志
		return new ResponseBean<>(logService.insert(log) > 0);
	}

	/**
	 * 删除日志.
	 *
	 * @param id id
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2018/10/31 21:27
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('monitor:log:del') or hasAnyRole('" + SecurityConstant.ROLE_ADMIN + "')")
	@ApiOperation(value = "删除日志", notes = "根据ID删除日志")
	@ApiImplicitParam(name = "id", value = "日志ID", required = true, paramType = "path")
	public ResponseBean<Boolean> delete(@PathVariable Long id) {
		Log log = new Log();
		log.setId(id);
		return new ResponseBean<>(logService.delete(log) > 0);
	}

	/**
	 * 批量删除.
	 *
	 * @param ids ids
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2018/12/4 10:12
	 */
	@PostMapping("deleteAll")
	@PreAuthorize("hasAuthority('monitor:log:del') or hasAnyRole('" + SecurityConstant.ROLE_ADMIN + "')")
	@ApiOperation(value = "批量删除日志", notes = "根据日志ids批量删除日志")
	@ApiImplicitParam(name = "ids", value = "日志ID", dataType = "Long")
	public ResponseBean<Boolean> deleteAllLog(@RequestBody Long[] ids) {
		boolean success = false;
		try {
			if (!ArrayUtils.isEmpty(ids)) {
				success = logService.deleteAll(ids) > 0;
			}
		}
		catch (Exception e) {
			log.error("删除附件失败！", e);
		}
		return new ResponseBean<>(success);
	}
}
