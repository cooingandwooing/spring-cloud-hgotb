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

package com.github.cooingandwooing.common.core.model;

import java.io.Serializable;

import lombok.Data;


/**
 * 封装返回数据.
 *
 * @author gaoxiaofeng
 * @date 2019/3/17 12:08
 */
@Data
public class ResponseBean<T> implements Serializable {

	/**
	 * 序列化.
	 */
	public static final long serialVersionUID = 42L;

	/**
	 * 没有登录.
	 */
	public static final int NO_LOGIN = -1;

	/**
	 * 成功.
	 */
	public static final int SUCCESS = 200;

	/**
	 * 失败.
	 */
	public static final int FAIL = 500;

	/**
	 * 验证码错误.
	 */
	public static final int INVALID_VALIDATE_CODE_ERROR = 478;

	/**
	 * 验证码过期错误.
	 */
	public static final int VALIDATE_CODE_EXPIRED_ERROR = 479;

	/**
	 * 用户名不存在或密码错误.
	 */
	public static final int USERNAME_NOT_FOUND_OR_PASSWORD_ERROR = 400;

	/**
	 * 当前操作没有权限.
	 */
	public static final int UNAUTHORIZED = 401;

	/**
	 * 当前操作没有权限.
	 */
	public static final int NO_PERMISSION = 403;

	private String msg = "success";

	private int code = SUCCESS;

	/**
	 * http 状态码.
	 */
	private int status = 200;

	private T data;

	public ResponseBean() {
		super();
	}

	public ResponseBean(T data) {
		super();
		this.data = data;
	}

	public ResponseBean(T data, String msg) {
		super();
		this.data = data;
		this.msg = msg;
	}

	public ResponseBean(Throwable e) {
		super();
		this.msg = e.getMessage();
		this.code = FAIL;
	}

	public ResponseBean(Throwable e, int code) {
		super();
		this.msg = e.getMessage();
		this.code = code;
	}
}
