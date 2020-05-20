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

package com.github.cooingandwooing.user.config;

import java.util.Properties;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置.
 *
 * @author gaoxiaofeng
 * @date 2018-09-14-20:34
 */
@Configuration
public class ValidateCodeConfig {

	@Bean
	public DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "no");
		// 验证码字体颜色
		properties.put("kaptcha.textproducer.font.color", "black");
		// 验证码字体大小
		properties.put("kaptcha.textproducer.font.size", "40");
		// 验证码文本字符间距
		properties.put("kaptcha.textproducer.char.space", "6");
		// 验证码宽度
		properties.put("kaptcha.image.width", "120");
		// 验证码高度
		properties.put("kaptcha.image.height", "50");
		properties.put("kaptcha.textproducer.char.length", "4");
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
