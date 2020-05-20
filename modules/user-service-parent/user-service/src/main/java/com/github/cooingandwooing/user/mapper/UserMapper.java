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
import com.github.cooingandwooing.common.core.vo.UserVo;
import com.github.cooingandwooing.user.api.module.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper接口.
 *
 * @author cooingandwooing
 * @date 2018-08-25 15:27
 */
@Mapper
public interface UserMapper extends CrudMapper<User> {

	/**
	 * 查询用户数量.
	 *
	 * @param userVo userVo
	 * @return Integer
	 */
	Integer userCount(UserVo userVo);
}
