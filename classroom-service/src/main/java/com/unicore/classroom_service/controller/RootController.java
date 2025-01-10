package com.unicore.classroom_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doc")
public class RootController {
    @GetMapping
    public String swaggerUi() {
        return "redirect:/webjars/swagger-ui/index.html";
    }
}
