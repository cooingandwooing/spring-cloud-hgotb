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

package com.github.cooingandwooing.user.service;

import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.RandomUtil;
import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.enums.LoginType;
import com.github.cooingandwooing.common.core.exceptions.ServiceException;
import com.github.cooingandwooing.common.core.model.ResponseBean;
import com.github.cooingandwooing.common.core.utils.ResponseUtil;
import com.github.cooingandwooing.common.security.constant.SecurityConstant;
import com.github.cooingandwooing.msc.api.constant.SmsConstant;
import com.github.cooingandwooing.msc.api.dto.SmsDto;
import com.github.cooingandwooing.msc.api.feign.MscServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 手机管理Service.
 *
 * @author cooingandwooing
 * @date 2019/07/02 09:35
 */
@Slf4j
@AllArgsConstructor
@Service
public class MobileService {

	private final UserService userService;

	private final RedisTemplate redisTemplate;

	private final MscServiceClient mscServiceClient;

	/**
	 * 发送短信.
	 *
	 * @param mobile     mobile
	 * @return ResponseBean
	 * @author cooingandwooing
	 * @date 2019/07/02 09:36:52
	 */
	public ResponseBean<Boolean> sendSms(String mobile) {
		String key = CommonConstant.DEFAULT_CODE_KEY + LoginType.SMS.getType() + "@" + mobile;
		// TODO 校验时间
		String code = RandomUtil.randomNumbers(Integer.parseInt(CommonConstant.CODE_SIZE));
		log.debug("手机号生成验证码成功:{},{}", mobile, code);
		redisTemplate.opsForValue().set(key, code, SecurityConstant.DEFAULT_SMS_EXPIRE, TimeUnit.SECONDS);
		// 调用消息中心服务，发送短信验证码
		SmsDto smsDto = new SmsDto();
		smsDto.setReceiver(mobile);
		smsDto.setContent(String.format(SmsConstant.SMS_TEMPLATE, code));
		ResponseBean<?> result = mscServiceClient.sendSms(smsDto);
		if (!ResponseUtil.isSuccess(result)) {
			throw new ServiceException("发送短信失败: " + result.getMsg());
		}
		log.info("发送验证码结果：{}", result.getData());
		return new ResponseBean<>(Boolean.TRUE, code);
	}
}
