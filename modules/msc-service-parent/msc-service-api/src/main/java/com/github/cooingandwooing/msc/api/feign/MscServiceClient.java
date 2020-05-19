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

package com.github.cooingandwooing.msc.api.feign;

import com.github.cooingandwooing.msc.api.dto.SmsDto;
import com.github.cooingandwooing.common.core.constant.ServiceConstant;
import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.feign.config.CustomFeignConfig;
import com.github.cooingandwooing.msc.api.feign.factory.MscServiceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 消息中心服务
 *
 * @author cooingandwooing
 * @date 2019/07/02 16:04
 */
@FeignClient(value = ServiceConstant.MSC_SERVICE, configuration = CustomFeignConfig.class, fallbackFactory = MscServiceClientFallbackFactory.class)
public interface MscServiceClient {

    /**
     * 发送短信
     *
     * @param smsDto smsDto
     * @return ResponseBean
     * @author cooingandwooing
     * @date 2019/07/02 16:07:27
     */
    @PostMapping("/v1/sms/sendSms")
    ResponseBean<?> sendSms(@RequestBody SmsDto smsDto);
}
