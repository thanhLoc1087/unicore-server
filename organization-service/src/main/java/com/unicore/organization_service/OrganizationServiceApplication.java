package com.unicore.organization_service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

@SpringBootApplication
@EnableR2dbcRepositories
public class OrganizationServiceApplication {

    public static void main(String[] args) {
        Map<String, Object> env = Dotenv.configure()
            // .directory("organization-service")
            .load()
            .entries()
            .stream()
            .collect(Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));
        new SpringApplicationBuilder(OrganizationServiceApplication.class)
                .environment(new StandardEnvironment() {
                    @Override
                    protected void customizePropertySources(MutablePropertySources propertySources) {
                        super.customizePropertySources(propertySources);
                        propertySources.addLast(new MapPropertySource("dotenvProperties", env));
                    }
                })
                .run(args);
		// SpringApplication.run(OrganizationServiceApplication.class, args);
    }
}
