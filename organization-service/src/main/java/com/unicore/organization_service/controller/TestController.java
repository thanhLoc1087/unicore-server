package com.unicore.organization_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String getMethodName() {
        return "HELLO WORLD";
    }
    
}
