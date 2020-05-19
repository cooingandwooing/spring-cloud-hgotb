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

package com.github.cooingandwooing.auth.security;

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.enums.LoginType;
import com.github.cooingandwooing.common.security.tenant.TenantContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展JwtAccessTokenConverter，增加租户code
 *
 * @author gaoxiaofeng
 * @date 2019/5/28 22:53
 */
public class CustomTokenConverter extends JwtAccessTokenConverter {

    /**
     * @see-SysUtil.getCurrentUserTenantCode
     * tenantCode修改为SecurityConstant.TENANT_CODE  unmodified
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        String grantType = authentication.getOAuth2Request().getGrantType();
        // 加入tenantCode
        additionalInfo.put("tenantCode", TenantContextHolder.getTenantCode());
        // 加入登录类型，用户名/手机号
        if (grantType.equalsIgnoreCase(CommonConstant.GRANT_TYPE_PASSWORD)) {
            additionalInfo.put("loginType", LoginType.PWD.getType());
        } else if (grantType.equalsIgnoreCase(CommonConstant.GRANT_TYPE_MOBILE)) {
            additionalInfo.put("loginType", LoginType.SMS.getType());
        } else if (grantType.equalsIgnoreCase(LoginType.WECHAT.getType())) {
            additionalInfo.put("loginType", LoginType.WECHAT.getType());
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return super.enhance(accessToken, authentication);
    }
}
