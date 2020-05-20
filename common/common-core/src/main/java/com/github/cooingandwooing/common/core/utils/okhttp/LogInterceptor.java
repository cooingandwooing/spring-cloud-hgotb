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

package com.github.cooingandwooing.common.core.utils.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OkHttp日志拦截器LoggingInterceptor.
 *
 * @author gaoxiaofeng
 * @date 2018-09-12 17:03
 */
public class LogInterceptor implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

	@Override
	public Response intercept(Chain chain) throws IOException {
		// 请求发起的时间
		long t1 = System.nanoTime();
		// Chain 里包含了request和response
		Request request = chain.request();
		logger.debug(String.format("sending %s request %s%n%s", request.method(),
			request.url(), request.headers()));
		Response response = chain.proceed(request);
		// 收到响应的时间
		long t2 = System.nanoTime();
		logger.debug(String.format("received response for %s in %.1fms%n%s", response.request().url(),
			(t2 - t1) / 1e6d, response.headers()));
		return response;
	}
}
