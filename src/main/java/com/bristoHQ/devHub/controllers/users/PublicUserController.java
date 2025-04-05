package com.bristoHQ.devHub.controllers.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bristoHQ.devHub.dto.user.UserDTO;
import com.bristoHQ.devHub.services.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/users")
@RequiredArgsConstructor
public class PublicUserController {

    private final UserServiceImpl userService;

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/byEmailOrUsername/{emailOrUsername}")
    public ResponseEntity<UserDTO> getUserByEmailOrUsername(@PathVariable String emailOrUsername) {
        UserDTO user = userService.findByEmailOrUsername(emailOrUsername, emailOrUsername);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/byToken")
    public ResponseEntity<UserDTO> getUserDetails(@RequestHeader("Authorization") String token) {
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return ResponseEntity.ok(userService.getUserDetails(actualToken));
    }

    @GetMapping("/isPremium/{emailOrUsername}")
    public ResponseEntity<Boolean> isUserPremium(@PathVariable String emailOrUsername) {
        return ResponseEntity.ok(userService.isUserPremiumByEmailOrUsername(emailOrUsername, emailOrUsername));
    }
}
