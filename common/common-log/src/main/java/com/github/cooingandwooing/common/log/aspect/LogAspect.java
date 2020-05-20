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

package com.github.cooingandwooing.common.log.aspect;

import com.github.cooingandwooing.common.core.utils.SpringContextHolder;
import com.github.cooingandwooing.common.core.utils.SysUtil;
import com.github.cooingandwooing.common.log.annotation.Log;
import com.github.cooingandwooing.common.log.event.LogEvent;
import com.github.cooingandwooing.common.log.utils.LogUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志切面，异步记录日志.
 *
 * @author gaoxiaofeng
 * @date 2019/3/12 23:52
 */
@Aspect
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Around("@annotation(log)")
	public Object around(ProceedingJoinPoint point, Log log) throws Throwable {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		logger.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
		com.github.cooingandwooing.common.core.model.Log logVo = LogUtil.getLog();
		logVo.setTitle(log.value());
		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Object obj = point.proceed();
		Long endTime = System.currentTimeMillis();
		logVo.setTime(String.valueOf(endTime - startTime));
		logVo.setCommonValue(SysUtil.getUser(), SysUtil.getSysCode());
		SpringContextHolder.publishEvent(new LogEvent(logVo));
		return obj;
	}
}
