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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import cn.hutool.core.collection.CollUtil;
import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import com.github.cooingandwooing.common.core.utils.TreeUtil;
import com.github.cooingandwooing.common.core.vo.DeptVo;
import com.github.cooingandwooing.common.core.web.BaseController;
import com.github.cooingandwooing.common.log.annotation.Log;
import com.github.cooingandwooing.common.security.constant.SecurityConstant;
import com.github.cooingandwooing.user.api.dto.DeptDto;
import com.github.cooingandwooing.user.api.module.Dept;
import com.github.cooingandwooing.user.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门controller.
 *
 * @author cooingandwooing
 * @date 2018/8/26 0026 22:49
 */
@AllArgsConstructor
@Api("部门信息管理")
@RestController
@RequestMapping("/v1/dept")
public class DeptController extends BaseController {

	private final DeptService deptService;

	/**
	 * 查询树形部门集合.
	 *
	 * @return List
	 * @author cooingandwooing
	 * @date 2018/10/25 12:57
	 */
	@GetMapping("depts")
	@ApiOperation("获取部门列表")
	public List<DeptDto> depts() {
		Dept dept = new Dept();
		dept.setApplicationCode(SysUtil.getSysCode());
		dept.setTenantCode(SysUtil.getTenantCode());
		// 查询部门集合
		Stream<Dept> deptStream = deptService.findList(dept).stream();
		if (Optional.ofNullable(deptStream).isPresent()) {
			List<DeptDto> deptTreeList = deptStream.map(DeptDto::new).collect(Collectors.toList());
			// 排序、构建树形结构
			return TreeUtil
				.buildTree(CollUtil.sort(deptTreeList, Comparator.comparingInt(DeptDto::getSort)), CommonConstant.ROOT);
		}
		return new ArrayList<>();
	}

	/**
	 * 根据id获取部门.
	 *
	 * @param id id
	 * @return Dept
	 * @author cooingandwooing
	 * @date 2018/8/28 10:11
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "获取部门信息", notes = "根据部门id获取部门详细信息")
	@ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "Long", paramType = "path")
	public Dept get(@PathVariable Long id) {
		Dept dept = new Dept();
		dept.setId(id);
		return deptService.get(dept);
	}

	/**
	 * 新增部门.
	 *
	 * @param dept dept
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2018/8/28 10:15
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('sys:dept:add') or hasAnyRole('" + SecurityConstant.ROLE_ADMIN + "')")
	@ApiOperation(value = "创建部门", notes = "创建部门")
	@ApiImplicitParam(name = "dept", value = "部门实体", required = true, dataType = "Dept")
	@Log("新增部门")
	public ResponseBean<Boolean> add(@RequestBody @Valid Dept dept) {
		dept.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode(), SysUtil.getTenantCode());
		return new ResponseBean<>(deptService.insert(dept) > 0);
	}

	/**
	 * 删除部门.
	 *
	 * @param id id
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2018/8/28 10:16
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('sys:dept:del') or hasAnyRole('" + SecurityConstant.ROLE_ADMIN + "')")
	@ApiOperation(value = "删除部门", notes = "根据ID删除部门")
	@ApiImplicitParam(name = "id", value = "部门ID", required = true, paramType = "path")
	@Log("删除部门")
	public ResponseBean<Boolean> delete(@PathVariable Long id) {
		Dept dept = new Dept();
		dept.setId(id);
		dept.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode(), SysUtil.getTenantCode());
		return new ResponseBean<>(deptService.delete(dept) > 0);
	}

	/**
	 * 更新部门.
	 *
	 * @param dept dept
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2018/8/28 10:22
	 */
	@PutMapping
	@PreAuthorize("hasAuthority('sys:dept:edit') or hasAnyRole('" + SecurityConstant.ROLE_ADMIN + "')")
	@ApiOperation(value = "更新部门信息", notes = "根据部门id更新部门的基本信息")
	@ApiImplicitParam(name = "dept", value = "部门实体", required = true, dataType = "Dept")
	@Log("更新部门")
	public ResponseBean<Boolean> update(@RequestBody @Valid Dept dept) {
		dept.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode(), SysUtil.getTenantCode());
		return new ResponseBean<>(deptService.update(dept) > 0);
	}

	/**
	 * 根据ID查询.
	 *
	 * @param ids ids
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2018/12/31 22:13
	 */
	@RequestMapping(value = "findById", method = RequestMethod.POST)
	@ApiOperation(value = "批量查询部门信息", notes = "根据Ids批量查询信息")
	@ApiImplicitParam(name = "ids", value = "部门ID", required = true, dataType = "Long")
	public ResponseBean<List<DeptVo>> findById(@RequestBody Long[] ids) {
		ResponseBean<List<DeptVo>> returnT = null;
		Stream<Dept> deptStream = deptService.findListById(ids).stream();
		if (Optional.ofNullable(deptStream).isPresent()) {
			List<DeptVo> deptVoList = deptStream.map(tempDept -> {
				DeptVo tempDeptVo = new DeptVo();
				BeanUtils.copyProperties(tempDept, tempDeptVo);
				return tempDeptVo;
			}).collect(Collectors.toList());
			returnT = new ResponseBean<>(deptVoList);
		}
		return returnT;
	}
}
