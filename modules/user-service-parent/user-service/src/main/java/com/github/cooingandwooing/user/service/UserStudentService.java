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

import com.github.cooingandwooing.common.core.service.CrudService;
import com.github.cooingandwooing.user.api.module.UserStudent;
import com.github.cooingandwooing.user.mapper.UserStudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 用户学生Service
 *
 * @author cooingandwooing
 * @date 2019/07/09 15:58
 */
@Service
public class UserStudentService extends CrudService<UserStudentMapper, UserStudent> {

    /**
     * 根据用户ID查询
     *
     * @param userId userId
     * @return List
     * @author cooingandwooing
     * @date 2019/07/09 17:01:13
     */
    public List<UserStudent> getByUserId(@NotBlank String userId) {
        return this.dao.getByUserId(userId);
    }

    /**
     * 根据学生ID查询
     *
     * @param studentId studentId
     * @return UserStudent
     * @author cooingandwooing
     * @date 2019/07/09 17:02:19
     */
    public UserStudent getByStudentId(@NotBlank String studentId) {
        return this.dao.getByStudentId(studentId);
    }

    /**
     * 根据用户id删除
     *
     * @param userId userId
     * @return int
     * @author cooingandwooing
     * @date 2019/07/09 17:04:13
     */
    @Transactional
    public int deleteByUserId(@NotBlank String userId) {
        return this.dao.deleteByUserId(userId);
    }

    /**
     * 根据学生id删除
     *
     * @param studentId studentId
     * @return int
     * @author cooingandwooing
     * @date 2019/07/09 17:04:59
     */
    @Transactional
    public int deleteByStudentId(@NotBlank String studentId) {
        return this.dao.deleteByStudentId(studentId);
    }
}
