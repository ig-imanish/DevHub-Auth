package com.bristoHQ.devHub.controllers.users;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bristoHQ.devHub.dto.MessageResponseDTO;
import com.bristoHQ.devHub.models.User;
import com.bristoHQ.devHub.services.PremiumService;
import com.bristoHQ.devHub.services.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceImpl userService;

    private final PremiumService premiumService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(401).body("Unauthorized: No user logged in");
        }

        User user = userService.findByEmailOrUsername(principal.getName(), principal.getName());
        System.out.println(" /me User: " + user);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(404).body("User not found");
    }

    @GetMapping("/byToken")
    public ResponseEntity<User> getUserDetails(@RequestHeader("Authorization") String token) {
        // Extract token value from "Bearer <token>"
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        User user = userService.getUserDetails(actualToken);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/byEmailUsername/{emailOrUsername}")
    public ResponseEntity<User> getUserByEmailOrUsername(@PathVariable String emailOrUsername) {
        User user = userService.findByEmailOrUsername(emailOrUsername, emailOrUsername);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/isPremium/{emailOrUsername}")
    public ResponseEntity<Boolean> isUserPremium(@PathVariable String emailOrUsername) {
        return new ResponseEntity<>(userService.isUserPremiumByEmailOrUsername(emailOrUsername, emailOrUsername),
                HttpStatus.OK);
    }

    @GetMapping("/redeem")
    public ResponseEntity<MessageResponseDTO> redeemPremium(@RequestParam String code, Principal principal) {
        return new ResponseEntity<>(premiumService.redeemPremium(code, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/info")
    public String displayUserInfo() {
        var securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {
            return "No authentication found!";
        }

        return "Authenticated user: " + authentication.getPrincipal().toString();
    }

}
