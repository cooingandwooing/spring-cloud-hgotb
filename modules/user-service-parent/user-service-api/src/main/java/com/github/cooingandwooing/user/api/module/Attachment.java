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

import com.github.cooingandwooing.user.api.constant.AttachmentConstant;
import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 附件信息
 *
 * @author gaoxiaofeng
 * @date 2018/10/30 20:47
 */
@Data
public class Attachment extends BaseEntity<Attachment> {

    /**
     * 附件名称
     */
    @NotBlank(message = "附件名称不能为空")
    private String attachName;

    /**
     * 附件大小
     */
    @NotBlank(message = "附件大小不能为空")
    private String attachSize;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 文件ID
     */
    private String fastFileId;

    /**
     * 业务流水号
     */
    @NotBlank(message = "附件业务流水号不能为空")
    private String busiId;

    /**
     * 业务类型
     */
    private String busiType = AttachmentConstant.BUSI_TYPE_NORMAL_ATTACHMENT;

    /**
     * 业务模块
     */
    private String busiModule;

    /**
     * 预览地址
     */
    private String previewUrl;
}
