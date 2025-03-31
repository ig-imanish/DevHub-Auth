package com.bristoHQ.devHub.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GlobalAccessController {
    
    @GetMapping("/auth/public")
    public String publicAccessAuth() {
        return "Service is up Auth";
    }

    @GetMapping("/users/public")
    public String publicAccessUsers() {
        return "Service is up Users";
    }
}
