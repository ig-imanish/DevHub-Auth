package com.bristoHQ.devHub.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GlobalAccessController {
    
    @GetMapping("/status")
    public String publicAccess() {
        return "Service is up";
    }
}
