package com.unicore.organization_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/")
public class RootController {
    @GetMapping
    public String swaggerUi() {
        log.info("LOCLOC: Swagger");
        return "redirect:/swagger-ui.html";
    }
}