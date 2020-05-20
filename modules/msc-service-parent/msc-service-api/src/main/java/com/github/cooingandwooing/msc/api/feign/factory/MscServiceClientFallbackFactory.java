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

package com.github.cooingandwooing.msc.api.feign.factory;

import com.github.cooingandwooing.msc.api.feign.MscServiceClient;
import com.github.cooingandwooing.msc.api.feign.fallback.MscServiceClientFallbackImpl;
import feign.hystrix.FallbackFactory;

import org.springframework.stereotype.Component;

/**
 * 消息中心服务断路器工厂.
 *
 * @author cooingandwooing
 * @date 2019/07/02 16:08
 */
@Component
public class MscServiceClientFallbackFactory implements FallbackFactory<MscServiceClient> {

	@Override
	public MscServiceClient create(Throwable throwable) {
		MscServiceClientFallbackImpl mscServiceClientFallback = new MscServiceClientFallbackImpl();
		mscServiceClientFallback.setThrowable(throwable);
		return mscServiceClientFallback;
	}
}
