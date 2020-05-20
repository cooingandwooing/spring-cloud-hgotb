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

package com.github.cooingandwooing.msc.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 封装短信服务返回的结果.
 *
 * @author cooingandwooing
 * @date 2019/08/04 13:51
 */
@Data
public class SmsResponse {

	@JsonProperty("Message")
	private String message;

	@JsonProperty("RequestId")
	private String requestId;

	@JsonProperty("Code")
	private String code;
}
