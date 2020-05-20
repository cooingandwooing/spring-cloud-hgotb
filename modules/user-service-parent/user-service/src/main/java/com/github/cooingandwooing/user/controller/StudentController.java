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

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.utils.PageUtil;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import com.github.cooingandwooing.common.core.web.BaseController;
import com.github.cooingandwooing.common.log.annotation.Log;
import com.github.cooingandwooing.user.api.dto.StudentDto;
import com.github.cooingandwooing.user.api.module.Student;
import com.github.cooingandwooing.user.service.StudentService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生管理Controller.
 *
 * @author cooingandwooing
 * @date 2019/07/09 15:29
 */
@Slf4j
@AllArgsConstructor
@Api("学生管理")
@RestController
@RequestMapping("/v1/students")
public class StudentController extends BaseController {

	private final StudentService studentService;

	/**
	 * 根据ID获取.
	 *
	 * @param id id
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/07/09 15:30
	 */
	@ApiOperation(value = "获取学生信息", notes = "根据学生id获取学生详细信息")
	@ApiImplicitParam(name = "id", value = "学生ID", required = true, dataType = "Long", paramType = "path")
	@GetMapping("/{id}")
	public ResponseBean<Student> student(@PathVariable Long id) {
		Student student = new Student();
		student.setId(id);
		return new ResponseBean<>(studentService.get(student));
	}

	/**
	 * 分页查询.
	 *
	 * @param pageNum    pageNum
	 * @param pageSize   pageSize
	 * @param sort       sort
	 * @param order      order
	 * @param studentDto studentDto
	 * @return PageInfo
	 * @author cooingandwooing
	 * @date 2019/07/09 15:31
	 */
	@GetMapping("studentList")
	@ApiOperation("获取学生列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = CommonConstant.PAGE_NUM, value = "分页页码", defaultValue = CommonConstant.PAGE_NUM_DEFAULT, dataType = "String"),
		@ApiImplicitParam(name = CommonConstant.PAGE_SIZE, value = "分页大小", defaultValue = CommonConstant.PAGE_SIZE_DEFAULT, dataType = "String"),
		@ApiImplicitParam(name = CommonConstant.SORT, value = "排序字段", defaultValue = CommonConstant.PAGE_SORT_DEFAULT, dataType = "String"),
		@ApiImplicitParam(name = CommonConstant.ORDER, value = "排序方向", defaultValue = CommonConstant.PAGE_ORDER_DEFAULT, dataType = "String"),
		@ApiImplicitParam(name = "studentDto", value = "学生信息", dataType = "StudentDto")
	})
	public PageInfo<Student> userList(@RequestParam(value = CommonConstant.PAGE_NUM, required = false, defaultValue = CommonConstant.PAGE_NUM_DEFAULT) String pageNum,
		@RequestParam(value = CommonConstant.PAGE_SIZE, required = false, defaultValue = CommonConstant.PAGE_SIZE_DEFAULT) String pageSize,
		@RequestParam(value = CommonConstant.SORT, required = false, defaultValue = CommonConstant.PAGE_SORT_DEFAULT) String sort,
		@RequestParam(value = CommonConstant.ORDER, required = false, defaultValue = CommonConstant.PAGE_ORDER_DEFAULT) String order,
		StudentDto studentDto) {
		Student student = new Student();
		BeanUtils.copyProperties(studentDto, student);
		student.setTenantCode(SysUtil.getTenantCode());
		return studentService.findPage(PageUtil.pageInfo(pageNum, pageSize, sort, order), student);
	}

	/**
	 * 创建学生.
	 *
	 * @param studentDto studentDto
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/07/09 15:31
	 */
	@PostMapping
	@ApiOperation(value = "新增学生", notes = "新增学生")
	@ApiImplicitParam(name = "studentDto", value = "学生实体student", required = true, dataType = "StudentDto")
	@Log("新增学生")
	public ResponseBean<Boolean> add(@RequestBody @Valid StudentDto studentDto) {
		return new ResponseBean<>(studentService.add(studentDto) > 0);
	}

	/**
	 * 更新学生.
	 *
	 * @param studentDto studentDto
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/07/09 15:32
	 */
	@PutMapping
	@ApiOperation(value = "更新学生信息", notes = "根据学生id更新学生的基本信息")
	@ApiImplicitParam(name = "studentDto", value = "学生实体student", required = true, dataType = "StudentDto")
	@Log("修改学生")
	public ResponseBean<Boolean> update(@RequestBody @Valid StudentDto studentDto) {
		try {
			Student student = new Student();
			BeanUtils.copyProperties(studentDto, student);
			student.setId(studentDto.getId());
			student.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode(), SysUtil.getTenantCode());
			return new ResponseBean<>(studentService.update(student) > 0);
		}
		catch (Exception e) {
			log.error("更新学生信息失败！", e);
		}
		return new ResponseBean<>(Boolean.FALSE);
	}

	/**
	 * 删除学生.
	 *
	 * @param id id
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/07/09 15:33
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除学生", notes = "根据ID删除学生")
	@ApiImplicitParam(name = "id", value = "学生ID", required = true, paramType = "path")
	@Log("删除学生")
	public ResponseBean<Boolean> delete(@PathVariable Long id) {
		try {
			Student student = new Student();
			student.setId(id);
			student = studentService.get(student);
			student.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode());
			studentService.delete(student);
		}
		catch (Exception e) {
			log.error("删除学生信息失败！", e);
		}
		return new ResponseBean<>(Boolean.FALSE);
	}

	/**
	 * 批量删除.
	 *
	 * @param ids ids
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/07/09 15:34
	 */
	@PostMapping("deleteAll")
	@ApiOperation(value = "批量删除学生", notes = "根据学生id批量删除学生")
	@ApiImplicitParam(name = "ids", value = "学生ID", dataType = "Long")
	@Log("批量删除学生")
	public ResponseBean<Boolean> deleteAll(@RequestBody Long[] ids) {
		boolean success = false;
		try {
			if (!ArrayUtils.isEmpty(ids)) {
				success = studentService.deleteAll(ids) > 0;
			}
		}
		catch (Exception e) {
			log.error("删除学生失败！", e);
		}
		return new ResponseBean<>(success);
	}

	/**
	 * 根据ID查询.
	 *
	 * @param ids ids
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/07/09 15:34
	 */
	@RequestMapping(value = "findById", method = RequestMethod.POST)
	@ApiOperation(value = "根据ID查询学生", notes = "根据ID查询学生")
	@ApiImplicitParam(name = "ids", value = "学生ID", required = true, paramType = "Long")
	public ResponseBean<List<Student>> findById(@RequestBody Long[] ids) {
		List<Student> studentList = studentService.findListById(ids);
		return Optional.ofNullable(studentList).isPresent() ? new ResponseBean<>(studentList) : null;
	}
}
