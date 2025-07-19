package com.floriano.login_system_backend_maven.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

    @GetMapping
    public String hi() {
        return "Hello fellas";
    }
}
