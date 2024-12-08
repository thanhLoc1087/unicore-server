package com.unicore.profile_service;

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
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableR2dbcRepositories
@Slf4j
public class ProfileServiceApplication {

	public static void main(String[] args) {
        // Map<String, Object> env = Dotenv.configure()
        //     // .directory("profile-service")
        //     .load()
        //     .entries()
        //     .stream()
        //     .collect(Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));
        // new SpringApplicationBuilder(ProfileServiceApplication.class)
        //         .environment(new StandardEnvironment() {
        //             @Override
        //             protected void customizePropertySources(MutablePropertySources propertySources) {
        //                 super.customizePropertySources(propertySources);
        //                 propertySources.addLast(new MapPropertySource("dotenvProperties", env));
        //             }
        //         })
        //         .run(args);
		SpringApplication.run(ProfileServiceApplication.class, args);
	}

}
