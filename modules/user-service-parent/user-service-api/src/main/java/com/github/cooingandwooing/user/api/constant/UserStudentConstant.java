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

package com.github.cooingandwooing.user.api.constant;

/**
 * 用户学生常量.
 *
 * @author cooingandwooing
 * @date 2019-07-25 13:08
 */
public class UserStudentConstant {

	protected UserStudentConstant() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 爸爸.
	 */
	public static final Integer RELATIONSHIP_TYPE_FATHER = 0;

	/**
	 * 妈妈.
	 */
	public static final Integer RELATIONSHIP_TYPE_MOTHER = 1;

	/**
	 * 爷爷.
	 */
	public static final Integer RELATIONSHIP_TYPE_GRAND_FATHER = 2;

	/**
	 * 奶奶.
	 */
	public static final Integer RELATIONSHIP_TYPE_GRAND_MOTHER = 3;

	/**
	 * 外公.
	 */
	public static final Integer RELATIONSHIP_TYPE_GRANDPA_FATHER = 5;

	/**
	 * 外婆.
	 */
	public static final Integer RELATIONSHIP_TYPE_GRANDMA_MOTHER = 6;
}
