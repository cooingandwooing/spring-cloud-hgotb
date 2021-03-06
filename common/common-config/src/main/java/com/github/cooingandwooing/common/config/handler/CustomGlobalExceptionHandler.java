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

package com.github.cooingandwooing.common.config.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.github.cooingandwooing.common.core.exceptions.CommonException;
import com.github.cooingandwooing.common.core.model.ResponseBean;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * 全局异常处理.
 *
 * @author gaoxiaofeng
 * @date 2019/11/20 15:56
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * 处理参数校验异常.
	 *
	 * @param ex      ex
	 * @param headers headers
	 * @param status  status
	 * @param request request
	 * @return ResponseEntity
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// 获取所有异常信息
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
		ResponseBean<List<String>> responseBean = new ResponseBean<>(errors);
		responseBean.setStatus(status.value());
		return new ResponseEntity<>(responseBean, headers, status);
	}

	/**
	 * 处理CommonException.
	 *
	 * @param e e
	 * @return ResponseEntity
	 */
	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ResponseBean<String>> handleCommonException(Exception e) {
		ResponseBean<String> responseBean = new ResponseBean<>();
		responseBean.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		responseBean.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		responseBean.setMsg(e.getMessage());
		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}
}
