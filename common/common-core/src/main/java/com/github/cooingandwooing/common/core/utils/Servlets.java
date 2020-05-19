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

package com.github.cooingandwooing.common.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * Servlet工具类.
 *
 * @author gaoxiaofeng
 * @date 2018/10/30 22:34
 */
public class Servlets {
	protected Servlets() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取在不同浏览器下的下载文件的转码规则.
	 *
	 * @param request  request
	 * @param fileName 原名，不进行转码前名称
	 * @return String 示例 "filename=\"" + downloadName + "\"";
	 * @date 2018年10月30日 22:35:05
	 */
	public static String getDownName(HttpServletRequest request, String fileName) {
		String result = "";
		String httpUserAgent = request.getHeader("User-Agent");
		try {
			if (StringUtils.isNotEmpty(httpUserAgent)) {
				String downloadName = URLEncoder.encode(fileName, "UTF-8");
				httpUserAgent = httpUserAgent.toLowerCase();
				if (httpUserAgent.contains("msie")) {
					result = "attachment;filename=\"" + downloadName + "\"";
				} // Opera浏览器只能采用filename*
				else if (httpUserAgent.contains("opera")) {
					result = "attachment;filename*=UTF-8''" + downloadName;
				}
				else if (httpUserAgent.contains("trident") || httpUserAgent.contains("edge")) {
					result = "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8");
				}
				// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
				else if (httpUserAgent.contains("applewebkit")) {
					result = "attachment;filename=\"" + downloadName + "\"";
				}
				// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
				else if (httpUserAgent.contains("mozilla")) {
					result = "attachment;filename*=UTF-8''" + downloadName;
				}
				else {
					result = "attachment;filename=\"" + downloadName + "\"";
				}
			}
		}
		catch (UnsupportedEncodingException e) {
			result = "attachment;filename=\"" + fileName + "\"";
		}
		return result;
	}
}
