package com.bristoHQ.devHub.controllers.users;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bristoHQ.devHub.dto.MessageResponseDTO;
import com.bristoHQ.devHub.dto.auth.BearerToken;
import com.bristoHQ.devHub.dto.user.UserDTO;
import com.bristoHQ.devHub.services.premium.PremiumService;
import com.bristoHQ.devHub.services.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class PrivateUserController {

    private final UserServiceImpl userService;
    private final PremiumService premiumService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(401).body("Unauthorized: No user logged in");
        }
        UserDTO user = userService.findByEmailOrUsername(principal.getName(), principal.getName());
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(404).body("User not found");
    }

    @GetMapping("/redeem")
    public ResponseEntity<MessageResponseDTO> redeemPremium(@RequestParam String code, Principal principal) {
        return ResponseEntity.ok(premiumService.redeemPremium(code, principal.getName()));
    }

    @GetMapping("/info")
    public String displayUserInfo() {
        var securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return (authentication == null) ? "No authentication found!" : "Authenticated user: " + authentication.getPrincipal().toString();
    }

    @PutMapping("/username")
    public ResponseEntity<?> updateUsername(@RequestParam String username, Principal principal) {
        UserDTO user = userService.findByEmailOrUsername(principal.getName(), principal.getName());
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        if (user.getUsername().equalsIgnoreCase(username)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Username is the same as the current username");
        }
        if (userService.findByUsername(username) != null) {
            return ResponseEntity.status(HttpStatus.IM_USED).body("Username already exists");
        }

        userService.updateUsername(principal.getName(), username);
        return ResponseEntity.ok(new BearerToken(userService.newJwtToken(username), "Bearer "));
    }
}
