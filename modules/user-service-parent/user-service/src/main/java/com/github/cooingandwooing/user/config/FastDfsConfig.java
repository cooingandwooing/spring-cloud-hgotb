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

package com.github.cooingandwooing.user.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * fastDfs配置
 * https://github.com/tobato/FastDFS_Client
 * 在Maven当中配置依赖以后，SpringBoot项目将会自动导入FastDFS依赖(感谢@Lzgabel)。
 *
 * 本类采用 FastDFS-Client 1.26.4版本以前引入方式，疑惑？？？
 * 将FastDFS-Client客户端引入本地化项目的方式非常简单，在SpringBoot项目/src/[com.xxx.主目录]/conf当中配置 本类
 * 本版本 1.26.7 应该能自动引入
 * @author gaoxiaofeng
 * @date 2018-01-05 14:45
 */
@Configuration
@Import(FdfsClientConfig.class)
// 解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastDfsConfig {

}

