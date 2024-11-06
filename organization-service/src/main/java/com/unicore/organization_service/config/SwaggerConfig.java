// package com.unicore.organization_service.config;


// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.service.Contact;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @Configuration
// @EnableSwagger2
// public class SwaggerConfig {
// 	ApiInfo apiInfo() {
// 		return new ApiInfoBuilder().title("Organization Service for Unicore")
// 				.description("")
// 				.termsOfServiceUrl("").version("0.0.1-SNAPSHOT").contact(new Contact("Thanh Loc", "https://github.com/thanhLoc1087", "thanhlocne246@gmail.com")).build();
// 	}

// 	@Bean
// 	public Docket configureControllerPackageAndConvertors() {
// 		return new Docket(DocumentationType.SWAGGER_2).select()
// 				.apis(RequestHandlerSelectors.basePackage("com.unicore.organization_service.controller")).build()
// 	                .apiInfo(apiInfo());
// 	}
// }