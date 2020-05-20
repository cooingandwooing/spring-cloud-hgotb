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
 * @author cooingandwooing
 * @date 2019/5/27 10:25
 */
public class TenantConstant {

	protected TenantConstant() {
		throw new UnsupportedOperationException();
	}
	/**
	 * 待审核.
	 */
	public static final Integer PENDING_AUDIT = 0;

	/**
	 * 审核通过.
	 */
	public static final Integer APPROVAL = 1;

	/**
	 * 审核不通过.
	 */
	public static final Integer AUDIT_FAIL = 2;
}
