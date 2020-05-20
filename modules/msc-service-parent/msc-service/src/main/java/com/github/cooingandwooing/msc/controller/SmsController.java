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

package com.github.cooingandwooing.msc.controller;

import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.web.BaseController;
import com.github.cooingandwooing.msc.api.dto.SmsDto;
import com.github.cooingandwooing.msc.api.model.SmsResponse;
import com.github.cooingandwooing.msc.service.SmsService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送短信接口.
 *
 * @author cooingandwooing
 * @date 2019/6/22 12:59
 */
@Slf4j
@AllArgsConstructor
@Api("发送短信")
@RestController
@RequestMapping("/v1/sms")
public class SmsController extends BaseController {

	private final SmsService smsService;

	/**
	 * 发送短信.
	 *
	 * @param smsDto smsDto
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/06/22 13:12
	 */
	@PostMapping("sendSms")
	public ResponseBean<SmsResponse> sendSms(@RequestBody SmsDto smsDto) {
		log.info("发送短信给{}，发送内容：{}", smsDto.getReceiver(), smsDto.getContent());
		SmsResponse smsResponse = smsService.sendSms(smsDto);
		log.info("发送短信成功，返回内容：{}", smsResponse);
		return new ResponseBean<>(smsResponse);
	}
}
