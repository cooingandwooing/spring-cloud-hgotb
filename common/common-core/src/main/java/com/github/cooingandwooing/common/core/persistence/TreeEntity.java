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

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 树形实体.
 *
 * @author gaoxiaofeng
 * @date 2018-09-13 20:40
 */
@Data
public abstract class TreeEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 7265456426423066026L;

	/**
	 * code.
	 */
	protected String code;

	/**
	 * 父节点.
	 */
	protected T parent;

	/**
	 * 父节点id.
	 */
	protected Long parentId;

	/**
	 * 排序号.
	 */
	protected Integer sort;

	/**
	 * 子节点.
	 */
	protected List<TreeEntity> children = new ArrayList<>();

	public void add(TreeEntity node) {
		children.add(node);
	}
}

