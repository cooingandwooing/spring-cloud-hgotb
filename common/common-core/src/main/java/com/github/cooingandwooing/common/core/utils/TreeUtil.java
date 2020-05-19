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

package com.github.cooingandwooing.common.core.utils;

import java.util.ArrayList;
import java.util.List;

import com.github.cooingandwooing.common.core.persistence.TreeEntity;

/**
 * @author gaoxiaofeng.
 * @date 2018-10-01 15:38
 */
public class TreeUtil {

	protected TreeUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 两层循环实现建树.
	 *
	 * @param treeEntities 传入的树实体列表
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> buildTree(List<? extends TreeEntity<T>> treeEntities, Object root) {
		List<TreeEntity<T>> treeEntityArrayList = new ArrayList<>();
		treeEntities.forEach(treeEntity -> {
			if (treeEntity.getParentId().equals(root)) {
				treeEntityArrayList.add(treeEntity);
			}
			treeEntities.forEach(childTreeEntity -> {
				if (childTreeEntity.getParentId().equals(treeEntity.getId())) {
					if (treeEntity.getChildren() == null) {
						treeEntity.setChildren(new ArrayList<>());
					}
					treeEntity.add(childTreeEntity);
				}
			});
		});
		return (List<T>) treeEntityArrayList;
	}
}
