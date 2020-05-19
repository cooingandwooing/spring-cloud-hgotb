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

package com.github.cooingandwooing.common.security.constant;

/**
 * @author gaoxiaofeng
 * @date 2018-08-25 14:08
 */
public class SecurityConstant {

    /**
     * 基础角色
     */
    public static final String BASE_ROLE = "role_user";

    /**
     * 超级管理员角色
     */
    public static final String ROLE_ADMIN = "role_admin";

    /**
     * 租户管理员角色
     */
    public static final String ROLE_TENANT_ADMIN = "role_tenant_admin";

    /**
     * 默认生成图形验证码过期时间
     */
    public static final int DEFAULT_IMAGE_EXPIRE = 60;

    /**
     * 默认短信验证码过期时间
     */
    public static final int DEFAULT_SMS_EXPIRE = 15 * 60;

    /**
     * 正常状态
     */
    public static final String NORMAL = "0";

    /**
     * 异常状态
     */
    public static final String ABNORMAL = "1";

    /**
     * 手机登录URL
     */
    public static final String MOBILE_TOKEN_URL = "/mobile/token";

    /**
     * 微信登录URL
     */
    public static final String WX_TOKEN_URL = "/wx/token";

    /**
     * 租户编号请求头
     */
    public static final String TENANT_CODE_HEADER = "Tenant-Code";

    /**
     * 默认系统编号
     */
    public static final String SYS_CODE = "POETRY";

    /**
     * 默认租户编号
     */
    public static final String DEFAULT_TENANT_CODE = "gitee";

    /**
     * 租户编号
     */
    public static final String TENANT_CODE = "tenantCode";

    /**
     * JSON 资源
     */
    public static final String CONTENT_TYPE = "application/json; charset=utf-8";
}
