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

package com.github.cooingandwooing.msc.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.github.cooingandwooing.common.core.exceptions.CommonException;
import com.github.cooingandwooing.common.core.utils.JsonMapper;
import com.github.cooingandwooing.msc.api.dto.SmsDto;
import com.github.cooingandwooing.msc.api.model.SmsResponse;
import com.github.cooingandwooing.msc.properties.SmsProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * @author cooingandwooing
 * @date 2019/6/22 13:23
 */
@Slf4j
@AllArgsConstructor
@Service
public class SmsService {

	private final SmsProperties smsProperties;

	/**
	 * 发送短信.
	 *
	 * @param smsDto smsDto
	 * @return SmsResponse
	 * @author cooingandwooing
	 * @date 2019/06/22 13:28
	 */
	public SmsResponse sendSms(SmsDto smsDto) {
		DefaultProfile profile = DefaultProfile
			.getProfile(smsProperties.getRegionId(), smsProperties.getAppKey(), smsProperties.getAppSecret());
		IAcsClient client = new DefaultAcsClient(profile);
		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain(smsProperties.getDomain());
		request.putQueryParameter("RegionId", smsProperties.getRegionId());
		request.putQueryParameter("PhoneNumbers", smsDto.getReceiver());
		request.putQueryParameter("SignName", smsProperties.getSignName());
		request.putQueryParameter("TemplateCode", smsProperties.getTemplateCode());
		request.putQueryParameter("TemplateParam", smsDto.getContent());
		request.setVersion(smsProperties.getVersion());
		request.setAction(smsProperties.getAction());
		try {
			CommonResponse response = client.getCommonResponse(request);
			log.info("发送结果：{}", response.getData());
			if (response.getHttpStatus() != 200) {
				throw new CommonException(response.getData());
			}
			SmsResponse smsResponse = JsonMapper.getInstance().fromJson(response.getData(), SmsResponse.class);
			if (smsResponse == null) {
				throw new CommonException("解析短信返回结果失败");
			}
			if (!"OK".equals(smsResponse.getCode())) {
				throw new CommonException(smsResponse.getMessage());
			}
			return smsResponse;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException("发送短信失败：" + e.getMessage());
		}
	}
}


