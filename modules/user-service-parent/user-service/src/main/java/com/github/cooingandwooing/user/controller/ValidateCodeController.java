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

package com.github.cooingandwooing.user.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.github.cooingandwooing.common.core.web.BaseController;
import com.github.cooingandwooing.user.service.UserService;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码.
 *
 * @author cooingandwooing
 * @date 2018-09-14-19:24
 */
@AllArgsConstructor
@Api("生成验证码")
@RestController
@RequestMapping("/v1/code")
public class ValidateCodeController extends BaseController {

	private final Producer producer;

	private final UserService userService;

	/**
	 * 生成验证码.
	 *
	 * @param random random
	 * @author cooingandwooing
	 * @date 2018/9/14 20:13
	 */
	@ApiOperation(value = "生成验证码", notes = "生成验证码")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "random", value = "随机数", required = true, dataType = "String", paramType = "path")
	})
	@GetMapping("/{random}")
	public void produceCode(@PathVariable String random, HttpServletResponse response) throws Exception {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		userService.saveImageCode(random, text);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		IOUtils.closeQuietly(out);
	}
}
