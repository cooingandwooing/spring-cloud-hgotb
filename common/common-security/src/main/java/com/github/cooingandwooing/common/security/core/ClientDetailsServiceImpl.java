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

package com.github.cooingandwooing.common.security.core;

import javax.sql.DataSource;

import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

/**
 * jdbc客户端service.
 *
 * @author gaoxiaofeng
 * @date 2019/3/30 23:30
 */
public class ClientDetailsServiceImpl extends JdbcClientDetailsService {

	public ClientDetailsServiceImpl(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 重写方法.
	 *
	 * @param clientId clientId
	 * @return ClientDetails
	 * @author gaoxiaofeng
	 * @date 2019/03/30 23:31
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
		return super.loadClientByClientId(clientId);
	}
}
