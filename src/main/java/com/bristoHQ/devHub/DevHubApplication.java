package com.bristoHQ.devHub;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bristoHQ.devHub.security.jwt.JwtUtilities;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class DevHubApplication {

    public static void main(String[] args) {
        // Load environment variables from .env file BEFORE Spring starts
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            dotenv.entries().forEach(e -> {
                System.setProperty(e.getKey(), e.getValue());
                System.out.println("Loaded env var: " + e.getKey());
            });
        } catch (Exception e) {
            System.err.println("Warning: Failed to load .env file. " + e.getMessage());
        }

        var context = SpringApplication.run(DevHubApplication.class, args);

        // ✅ Manually get the JwtUtilities bean from Spring Context
        JwtUtilities jwtUtilities = context.getBean(JwtUtilities.class);

        // ✅ Generate a test JWT token
        String token = jwtUtilities.generateToken("manishkukran123@gmail.com", Arrays.asList("USER"));
        System.out.println("Generated JWT Token: " + token);

        System.out.println("\n\n" +
                "**************************************************\n" +
                "*                                                *\n" +
                "*          Welcome to DevHub Application!        *\n" +
                "**************************************************\n" +
                "*                                                *\n" +
                "*      Your application has started successfully!*\n" +
                "*                                                *\n" +
                "**************************************************\n" +
                "\n");

        // System.out.println(jwtUtilities.generateToken("manishkukran123@gmail.com",
        // List.of("ROLE_USER")));
    }

}
