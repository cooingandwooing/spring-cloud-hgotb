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

package com.github.cooingandwooing.auth.api.module;

import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * OAuth2客户端信息
 *
 * @author gaoxiaofeng
 * @date 2019/3/30 16:35
 */
@Data
public class OauthClientDetails extends BaseEntity<OauthClientDetails> {

    /**
     * clientId
     */
    private String clientId;

    /**
     * resource_ids
     */
    private String resourceIds;

    /**
     * 密钥明文
     */
    private String clientSecretPlainText;

    /**
     * 密钥密文
     */
    private String clientSecret;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 授权类型
     */
    private String authorizedGrantTypes;

    /**
     * web_server_redirect_uri
     */
    private String webServerRedirectUri;

    /**
     * authorities
     */
    private String authorities;

    /**
     * access_token有效时间
     */
    private String accessTokenValidity;

    /**
     * refresh_token有效时间
     */
    private String refreshTokenValidity;

    /**
     * additional_information
     */
    private String additionalInformation;

    /**
     * autoapprove
     */
    private String autoapprove;

}
