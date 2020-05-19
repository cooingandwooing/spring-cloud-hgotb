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

-- ----------------------------
-- 2019年6月21日14:32:59
-- ----------------------------
ALTER TABLE `microservice-user`.`sys_attachment`
ADD COLUMN `previewUrl` varchar(255) NULL COMMENT '预览地址' AFTER `busi_type`;

-- ----------------------------
-- 2019年10月17日21:52:01
-- ----------------------------
ALTER TABLE `dev_microservice_exam`.`exam_examination_subject`
MODIFY COLUMN `examination_id` bigint(20) NULL COMMENT '考试ID' AFTER `id`,
ADD COLUMN `category_id` bigint(20) NULL COMMENT '分类' AFTER `examination_id`;