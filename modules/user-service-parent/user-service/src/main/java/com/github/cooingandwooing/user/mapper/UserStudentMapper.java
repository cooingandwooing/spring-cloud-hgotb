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

package com.github.cooingandwooing.user.mapper;

import com.github.cooingandwooing.common.core.persistence.CrudMapper;
import com.github.cooingandwooing.user.api.module.UserStudent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户学生Mapper
 *
 * @author cooingandwooing
 * @date 2019/07/09 15:57
 */
@Mapper
public interface UserStudentMapper extends CrudMapper<UserStudent> {

    /**
     * 根据userId查询
     *
     * @param userId userId
     * @return List
     */
    List<UserStudent> getByUserId(String userId);

    /**
     * 根据studentId查询
     *
     * @param studentId studentId
     * @return UserStudent
     */
    UserStudent getByStudentId(String studentId);

    /**
     * 根据用户id删除
     *
     * @param userId userId
     * @return int
     */
    int deleteByUserId(String userId);

    /**
     * 根据学生id删除
     *
     * @param studentId studentId
     * @return int
     */
    int deleteByStudentId(String studentId);
}
