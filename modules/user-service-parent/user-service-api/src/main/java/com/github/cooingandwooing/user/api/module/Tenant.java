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

package com.github.cooingandwooing.user.api.module;

import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 租户
 *
 * @author cooingandwooing
 * @date 2019/5/22 22:44
 */
@Data
public class Tenant extends BaseEntity<Tenant> {

    /**
     * 租户标识
     */
    @NotBlank(message = "租户标识不能为空")
    private String tenantCode;

    /**
     * 租户名称
     */
    @NotBlank(message = "租户名称不能为空")
    private String tenantName;

    /**
     * 租户描述信息
     */
    private String tenantDesc;

    /**
     * 状态，0-待审核，1-正常，2-审核不通过
     */
    private Integer status;
}
