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

import com.github.cooingandwooing.common.security.mobile.MobileUser;
import com.github.cooingandwooing.common.security.wx.WxUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 查询用户信息接口
 *
 * @author gaoxiaofeng
 * @date 2019/5/28 21:05
 */
public interface CustomUserDetailsService {

    /**
     * 根据用户名和租户标识查询
     *
     * @param username   username
     * @param tenantCode tenantCode
     * @return UserDetails
     * @author tangyi
     * @date 2019/05/28 21:06
     */
    UserDetails loadUserByIdentifierAndTenantCode(String username, String tenantCode) throws UsernameNotFoundException;

    /**
     * 根据社交账号和租户标识查询
     *
     * @param social     social
     * @param tenantCode tenantCode
     * @param mobileUser mobileUser
     * @return UserDetails
     * @author tangyi
     * @date 2019/06/22 21:08
     */
    UserDetails loadUserBySocialAndTenantCode(String social, String tenantCode, MobileUser mobileUser) throws UsernameNotFoundException;

    /**
     * 根据微信openId和租户标识查询
     *
     * @param code       code
     * @param tenantCode tenantCode
     * @param wxUser     wxUser
     * @return UserDetails
     * @author tangyi
     * @date 2019/07/05 20:04:59
     */
    UserDetails loadUserByWxCodeAndTenantCode(String code, String tenantCode, WxUser wxUser) throws UsernameNotFoundException;
}
