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

package com.github.cooingandwooing.common.core.utils.okhttp;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * @author gaoxiaofeng.
 * @version V1.0
 * @date 2018-09-09 10:14
 */
public class OkHttpRequestBodyUtil {

	protected OkHttpRequestBodyUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @author gaoxiaofeng
	 * @date 2018/9/9 10:22
	 */
	public static RequestBody create(final MediaType mediaType, final InputStream inputStream) {
		return new RequestBody() {
			@Override
			public MediaType contentType() {
				return mediaType;
			}

			@Override
			public long contentLength() {
				try {
					return inputStream.available();
				}
				catch (IOException e) {
					return 0;
				}
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				Source source = null;
				try {
					source = Okio.source(inputStream);
					sink.writeAll(source);
				}
				finally {
					Util.closeQuietly(source);
				}
			}
		};
	}
}
