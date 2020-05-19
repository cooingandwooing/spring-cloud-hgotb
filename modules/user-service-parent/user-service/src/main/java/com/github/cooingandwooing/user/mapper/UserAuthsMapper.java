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
import com.github.cooingandwooing.user.api.module.UserAuths;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserAuthsMapper
 *
 * @author cooingandwooing
 * @date 2019/07/03 11:44
 */
@Mapper
public interface UserAuthsMapper extends CrudMapper<UserAuths> {

    /**
     * 根据唯一标识查询
     *
     * @param userAuths userAuths
     * @return UserAuths
     * @author cooingandwooing
     * @date 2019/07/03 11:52:27
     */
    UserAuths getByIdentifier(UserAuths userAuths);

    /**
     * 根据用户id批量查询
     *
     * @param userIds userIds
     * @return List
     * @author cooingandwooing
     * @date 2019/07/03 22:02:13
     */
    List<UserAuths> getListByUserIds(@Param("userIds") Long[] userIds);

    /**
     * 根据唯一标识删除
     *
     * @param userAuths userAuths
     * @return int
     * @author cooingandwooing
     * @date 2019/07/04 11:39:50
     */
    int deleteByIdentifier(UserAuths userAuths);


    /**
     * 根据用户ID删除
     *
     * @param userAuths userAuths
     * @return int
     * @author cooingandwooing
     * @date 2019/07/04 11:43:50
     */
    int deleteByUserId(UserAuths userAuths);

    /**
     * 批量插入
     *
     * @param userAuths userAuths
     * @return int
     * @author cooingandwooing
     * @date 2019-09-03 13:07
     */
    int insertBatch(List<UserAuths> userAuths);
}
