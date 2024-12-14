package com.unicore.organization_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class OrganizationServiceApplication {

    public static void main(String[] args) {
		SpringApplication.run(OrganizationServiceApplication.class, args);
    }
}
