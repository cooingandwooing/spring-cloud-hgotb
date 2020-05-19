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

import org.springframework.security.core.GrantedAuthority;

/**
 * GrantedAuthority封装
 * 怀疑这个类没起作用
 * @author gaoxiaofeng
 * @date 2019/3/17 14:29
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private static final long serialVersionUID = -5588742812922519390L;

    private String roleName;

    public GrantedAuthorityImpl(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
