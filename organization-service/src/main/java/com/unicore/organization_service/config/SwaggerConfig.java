package com.unicore.organization_service.config;

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
  
  @Value("${profiles}")
  private String test;

  @Bean
  public OpenAPI myOpenAPI() {
    log.info(test);
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Server URL in Production environment");

    Contact contact = new Contact();
    contact.setEmail("thanhlocne246@gmail.com");
    contact.setName("Unicore Team");
    contact.setUrl("https://github.com/thanhLoc1087");

    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("GOGO Travel API Documentation")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to Unicore Backend endpoints.")
        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}