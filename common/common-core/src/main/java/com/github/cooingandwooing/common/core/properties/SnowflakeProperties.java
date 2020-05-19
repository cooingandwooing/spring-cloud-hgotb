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

package com.github.cooingandwooing.common.core.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ID生成配置.
 *
 * @author gaoxiaofeng
 * @date 2019/4/26 10:47
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cluster")
public class SnowflakeProperties {

	/**
	 * 工作节点ID.
	 */
	private String workId;

	/**
	 * 数据中心ID.
	 */
	private String dataCenterId;
}
