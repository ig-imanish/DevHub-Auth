package com.bristoHQ.devHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;
@SpringBootApplication
@AllArgsConstructor
public class DevHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevHubApplication.class, args);
        System.out.println("DevHubApplication started");
    }
}
