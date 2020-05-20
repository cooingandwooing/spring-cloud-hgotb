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

package com.github.cooingandwooing.msc.properties;

import lombok.Data;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 短信相关配置.
 *
 * @author cooingandwooing
 * @date 2019/6/22 13:31
 */
@Data
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${sms}'.isEmpty()")
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

	/**
	 * appKey.
	 */
	private String appKey;

	/**
	 * appSecret.
	 */
	private String appSecret;

	/**
	 * regionId.
	 */
	private String regionId;

	/**
	 * domain.
	 */
	private String domain;

	/**
	 * 签名.
	 */
	private String signName;

	/**
	 * 模板code.
	 */
	private String templateCode;

	/**
	 * 版本.
	 */
	private String version;

	/**
	 * action.
	 */
	private String action;
}
