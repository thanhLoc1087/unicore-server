package com.unicore.classevent_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClasseventServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClasseventServiceApplication.class, args);
	}

}
