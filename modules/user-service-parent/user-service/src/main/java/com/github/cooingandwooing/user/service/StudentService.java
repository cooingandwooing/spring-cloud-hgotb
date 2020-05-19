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

package com.github.cooingandwooing.user.service;

import com.github.cooingandwooing.common.core.exceptions.CommonException;
import com.github.cooingandwooing.common.core.service.CrudService;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import com.github.cooingandwooing.common.core.vo.UserVo;
import com.github.cooingandwooing.user.api.constant.UserStudentConstant;
import com.github.cooingandwooing.user.api.dto.StudentDto;
import com.github.cooingandwooing.user.api.module.Student;
import com.github.cooingandwooing.user.api.module.UserStudent;
import com.github.cooingandwooing.user.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生Service
 *
 * @author cooingandwooing
 * @date 2019/07/09 15:28
 */
@AllArgsConstructor
@Service
public class StudentService extends CrudService<StudentMapper, Student> {

    private final UserService userService;

    private final UserStudentService userStudentService;

    /**
     * 新增学生
     *
     * @param studentDto studentDto
     * @return int
     * @author cooingandwooing
     * @date 2019/07/10 18:18:04
     */
    @Transactional
    public int add(StudentDto studentDto) {
        String currentUser = SysUtil.getUser(), tenantCode = SysUtil.getTenantCode();
        Long userId = studentDto.getUserId();
        if (userId != null) {
            // 查询当前用户
            UserVo userVo = userService.findUserByIdentifier(currentUser, tenantCode);
            if (userVo == null)
                throw new CommonException("获取当前用户详细信息失败");
            userId = userVo.getId();
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        student.setCommonValue(currentUser, SysUtil.getSysCode(), tenantCode);
        // 新增用户学生关系
        UserStudent userStudent = new UserStudent();
        userStudent.setCommonValue(currentUser, SysUtil.getSysCode(), tenantCode);
        userStudent.setUserId(userId);
        userStudent.setStudentId(student.getId());
        // 默认关系类型是爸爸
        if (studentDto.getRelationshipType() == null)
            userStudent.setRelationshipType(UserStudentConstant.RELATIONSHIP_TYPE_FATHER);
        userStudentService.insert(userStudent);
        // 保存学生
        return this.insert(student);
    }
}
