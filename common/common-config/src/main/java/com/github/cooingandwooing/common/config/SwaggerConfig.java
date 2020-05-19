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

package com.github.cooingandwooing.common.config;

import java.util.ArrayList;
import java.util.List;

import com.github.cooingandwooing.common.security.constant.SecurityConstant;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Swagger配置.
 *
 * @author gaoxiaofeng
 * @date 2019/3/26 16:26
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public Docket createRestApi() {
		List<Parameter> parameterList = new ArrayList<>();
		parameterList.add(authorizationParameter());
		parameterList.add(tenantCodeParameter());
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(parameterList);
	}

	/**
	 * Authorization 请求头.
	 *
	 * @return Parameter
	 */
	private Parameter authorizationParameter() {
		ParameterBuilder tokenBuilder = new ParameterBuilder();
		tokenBuilder.name("Authorization")
				.description("access_token")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false).build();
		return tokenBuilder.build();
	}

	/**
	 * Tenant-Code 请求头.
	 *
	 * @return Parameter
	 */
	private Parameter tenantCodeParameter() {
		ParameterBuilder tokenBuilder = new ParameterBuilder();
		tokenBuilder.name("Tenant-Code")
				.defaultValue(SecurityConstant.DEFAULT_TENANT_CODE)
				.description("租户标识")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false).build();
		return tokenBuilder.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Swagger API")
				.description("https://gitee.com/zhanzhanlusi/spring-cloud-hgotb")
				.termsOfServiceUrl("https://gitee.com/zhanzhanlusi/spring-cloud-hgotb")
				.contact(new Contact("gaoxiaofeng", "https://gitee.com/zhanzhanlusi/spring-cloud-hgotb", "554099752@qq.com"))
				.version("1.0.0")
				.build();
	}

	/**
	 * 显示swagger-ui.html文档展示页，还必须注入swagger资源：.
	 * 让DispathcherServlet不拦截swagger的静态资源，貌似解决文件上传swagger显示问题。
	 * @param registry registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
