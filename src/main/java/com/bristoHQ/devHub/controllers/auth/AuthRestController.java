package com.bristoHQ.devHub.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bristoHQ.devHub.dto.BearerToken;
import com.bristoHQ.devHub.dto.LoginDto;
import com.bristoHQ.devHub.dto.RegisterDto;
import com.bristoHQ.devHub.dto.UserDTO;
import com.bristoHQ.devHub.security.JwtUtilities;
import com.bristoHQ.devHub.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthRestController {

    private final UserService userService;
    private final JwtUtilities jwtUtilities;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam(value = "error", required = false) String error,
            @RequestBody LoginDto loginDto) {
        if (error != null) {
            return ResponseEntity.badRequest().body("Login failed");
        }
        boolean isOAuthUser = userService.isOAuthUser(loginDto.getEmailOrUsername());
        if (isOAuthUser) {
            return ResponseEntity.badRequest().body("Login failed! Try logging in with OAuth i.e. Google");
        }
        String token = userService.authenticate(loginDto);
        return ResponseEntity.ok(new BearerToken(token, "Bearer "));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = jwtUtilities.getToken(request);
        userService.blacklistToken(token);
        return ResponseEntity.ok("Logout successful, token blacklisted!");
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<Boolean> isAuthenticatedByToken(@RequestHeader("Authorization") String token) {
        // Extract token value from "Bearer <token>"
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        UserDTO user = userService.getUserDetails(actualToken);
        return ResponseEntity.ok(user != null);
    }

}
