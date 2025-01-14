/**
 * This file created at 2017年6月23日.
 *
 * Copyright (c) 2002-2017 crisis, Inc. All rights reserved.
 */
package com.meijm.basis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <code>{Swagger2Configuration}</code>
 *
 * @author chenke
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

	@Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.meijm.basis.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("test")
                .contact(new Contact("", null, null))
                .version("1.0")
                .build();
    }

}
