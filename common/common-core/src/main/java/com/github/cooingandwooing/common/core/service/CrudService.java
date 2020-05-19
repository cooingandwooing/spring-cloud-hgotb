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

package com.github.cooingandwooing.common.core.service;

import java.util.ArrayList;
import java.util.List;

import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import com.github.cooingandwooing.common.core.persistence.CrudMapper;
import com.github.cooingandwooing.common.core.utils.ReflectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @param <D> CrudMapper
 * @param <T> BaseEntity
 * @author gaoxiaofeng
 * @date 2018-08-25 17:22
 */
public abstract class CrudService<D extends CrudMapper<T>, T extends BaseEntity<T>> extends BaseService {

	@Autowired
	protected D dao;

	/**
	 * 根据id获取.
	 *
	 * @param id id
	 * @return T
	 * @throws Exception 非空
	 */
	@SuppressWarnings("unchecked")
	public T get(Long id) throws Exception {
		Class<T> entityClass = ReflectionUtil.getClassGenricType(getClass(), 1);
		T entity = entityClass.getConstructor(Long.class).newInstance("0");
		entity.setId(id);
		return dao.get(entity);
	}

	/**
	 * 获取单条数据.
	 *
	 * @param entity entity
	 * @return T
	 */
	public T get(T entity) {
		return dao.get(entity);
	}

	/**
	 * 查询列表.
	 *
	 * @param entity entity
	 * @return List
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}

	/**
	 * 查询列表.
	 *
	 * @param entity entity
	 * @return List
	 */
	public List<T> findAllList(T entity) {
		return dao.findList(entity);
	}

	/**
	 * 查询列表.
	 *
	 * @param ids ids
	 * @return List
	 */
	public List<T> findListById(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return new ArrayList<>();
		}
		return dao.findListById(ids);
	}

	/**
	 * 查询分页.
	 *
	 * @param page   page
	 * @param entity entity
	 * @return PageInfo
	 */
	public PageInfo<T> findPage(PageInfo<T> page, T entity) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return new PageInfo<>(dao.findList(entity));
	}

	/**
	 * 插入或更新.
	 *
	 * @param entity entity
	 * @return int
	 */
	@Transactional
	public int save(T entity) {
		if (entity.isNewRecord()) {
			return dao.insert(entity);
		}
		else {
			return dao.update(entity);
		}
	}

	/**
	 * 删除.
	 *
	 * @param entity entity
	 * @return int
	 */
	@Transactional
	public int delete(T entity) {
		return dao.delete(entity);
	}

	/**
	 * 批量删除.
	 *
	 * @param ids ids
	 * @return int
	 */
	@Transactional
	public int deleteAll(Long[] ids) {
		return this.dao.deleteAll(ids);
	}

	/**
	 * 插入数据.
	 *
	 * @param entity entity
	 * @return int
	 */
	@Transactional
	public int insert(T entity) {
		return dao.insert(entity);
	}

	/**
	 * 更新数据.
	 *
	 * @param entity entity
	 * @return int
	 */
	@Transactional
	public int update(T entity) {
		return dao.update(entity);
	}
}

