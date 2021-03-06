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

package com.github.cooingandwooing.common.feign.config;

import java.util.concurrent.TimeUnit;

import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * okHttp配置.
 *
 * @author gaoxiaofeng
 * @date 2019/07/02 22:31
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient.Builder()
			// 连接超时
			.connectTimeout(60, TimeUnit.SECONDS)
			// 读超时
			.readTimeout(60, TimeUnit.SECONDS)
			// 写超时
			.writeTimeout(60, TimeUnit.SECONDS)
			// 是否自动重连
			.retryOnConnectionFailure(true)
			.connectionPool(new ConnectionPool())
			// 日志拦截器
			//.addInterceptor(new LogInterceptor())
			.build();
	}
}
