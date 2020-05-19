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

package com.github.cooingandwooing.common.core.persistence;

import java.util.List;

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import org.apache.ibatis.annotations.Param;


/**
 * Crud Mapper.
 *
 * @author gaoxiaofeng
 * @date 2018-08-24 18:59
 */
public interface CrudMapper<T> extends BaseMapper {

	/**
	 * 获取单条数据.
	 *
	 * @param entity entity
	 * @return T
	 */
	T get(T entity);

	/**
	 * 获取列表数据.
	 *
	 * @param entity entity
	 * @return List
	 */
	List<T> findList(T entity);

	/**
	 * 获取全部列表数据.
	 *
	 * @param entity entity
	 * @return List
	 */
	List<T> findAllList(T entity);

	/**
	 * 根据id获取列表数据.
	 *
	 * @param ids ids
	 * @return List
	 */
	List<T> findListById(@Param(CommonConstant.PARAM_IDS) Long[] ids);

	/**
	 * 插入单条数据.
	 *
	 * @param entity entity
	 * @return int
	 */
	int insert(T entity);

	/**
	 * 更新单条数据.
	 *
	 * @param entity entity
	 * @return int
	 */
	int update(T entity);

	/**
	 * 删除单条数据.
	 *
	 * @param entity entity
	 * @return int
	 */
	int delete(T entity);

	/**
	 * 批量删除.
	 *
	 * @param ids ids
	 * @return int
	 */
	int deleteAll(@Param(CommonConstant.PARAM_IDS) Long[] ids);
}

