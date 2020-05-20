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

package com.github.cooingandwooing.msc.api.feign.fallback;

import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.msc.api.dto.SmsDto;
import com.github.cooingandwooing.msc.api.feign.MscServiceClient;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/**
 * 消息中心服务断路器.
 *
 * @author cooingandwooing
 * @date 2019/07/02 16:09
 */
@Slf4j
@Component
public class MscServiceClientFallbackImpl implements MscServiceClient {

	private Throwable throwable;

	@Override
	public ResponseBean<?> sendSms(SmsDto smsDto) {
		log.error("feign 发送短信失败:{}, {}, {}", smsDto.getReceiver(), smsDto.getContent(), throwable);
		return null;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
