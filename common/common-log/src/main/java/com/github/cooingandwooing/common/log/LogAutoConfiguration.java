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

package com.github.cooingandwooing.common.log;

import com.github.cooingandwooing.common.log.aspect.LogAspect;
import com.github.cooingandwooing.common.log.event.LogListener;
import com.github.cooingandwooing.user.api.feign.UserServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author gaoxiaofeng.
 * @date 2019/3/12 23:51
 */
@EnableAsync
@Configuration
@ConditionalOnWebApplication
public class LogAutoConfiguration {

	@Autowired
	private UserServiceClient userServiceClient;

	@Bean
	public LogListener sysLogListener() {
		return new LogListener(userServiceClient);
	}

	@Bean
	public LogAspect sysLogAspect() {
		return new LogAspect();
	}
}
