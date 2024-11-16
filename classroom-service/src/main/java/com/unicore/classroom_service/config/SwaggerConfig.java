package com.unicore.classroom_service.config;

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
// 		return new ApiInfoBuilder().title("Classroom Service for Unicore")
// 				.description("")
// 				.termsOfServiceUrl("").version("0.0.1-SNAPSHOT").contact(new Contact("Thanh Loc", "https://github.com/thanhLoc1087", "thanhlocne246@gmail.com")).build();
// 	}

// 	@Bean
// 	public Docket configureControllerPackageAndConvertors() {
// 		return new Docket(DocumentationType.SWAGGER_2).select()
// 				.apis(RequestHandlerSelectors.basePackage("com.unicore.classroom_service.controller")).build()
// 	                .apiInfo(apiInfo());
// 	}
// }

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SwaggerConfig {

  @Value("${unicore.openapi.dev-url}")
  private String devUrl;

  @Value("${unicore.openapi.prod-url}")
  private String prodUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    log.info("LOCLOCLOCLOCLOC");
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");

    Contact contact = new Contact();
    contact.setEmail("thanhlocne246@gmail.com");
    contact.setName("Unicore Team");
    contact.setUrl("https://github.com/thanhloc1087");

    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("Unicore Classroom API Documentation")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to manage Unicore classroom service.")
        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}