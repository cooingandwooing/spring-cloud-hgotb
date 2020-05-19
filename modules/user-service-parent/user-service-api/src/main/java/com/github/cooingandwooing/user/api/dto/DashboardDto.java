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

package com.github.cooingandwooing.user.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DashboardDto
 *
 * @author cooingandwooing
 * @date 2019-03-01 13:56
 */
@Data
public class DashboardDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 在线用户数量
     */
    private String onlineUserNumber;

    /**
     * 考试数量
     */
    private String examinationNumber;

    /**
     * 参与人数
     */
    private String examUserNumber;
}
