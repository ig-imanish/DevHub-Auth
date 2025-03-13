package com.bristoHQ.devHub.controllers.users;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bristoHQ.devHub.dto.BearerToken;
import com.bristoHQ.devHub.dto.MessageResponseDTO;
import com.bristoHQ.devHub.dto.UserDTO;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(401).body("Unauthorized: No user logged in");
        }

        UserDTO user = userService.findByEmailOrUsername(principal.getName(), principal.getName());
        System.out.println(" /me User: " + user);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(404).body("User not found");
    }

    @GetMapping("/byToken")
    public ResponseEntity<UserDTO> getUserDetails(@RequestHeader("Authorization") String token) {
        // Extract token value from "Bearer <token>"
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        UserDTO user = userService.getUserDetails(actualToken);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/byEmailUsername/{emailOrUsername}")
    public ResponseEntity<UserDTO> getUserByEmailOrUsername(@PathVariable String emailOrUsername) {
        UserDTO user = userService.findByEmailOrUsername(emailOrUsername, emailOrUsername);
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

    @PutMapping("/username")
    public ResponseEntity<?> updateUsername(@RequestParam String username, Principal principal) {
        UserDTO user = userService.findByEmailOrUsername(principal.getName(), principal.getName());
        if(user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        if(user.getUsername().equalsIgnoreCase(username)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Username is same as current username");
        }
        UserDTO alreadyExistUserByThisUsername = userService.findByUsername(username);
        if(alreadyExistUserByThisUsername != null){
            return ResponseEntity.status(HttpStatus.IM_USED).body("Username already exists");
        }
       System.out.println( userService.updateUsername(principal.getName(), username));
       System.out.println(userService.newJwtToken(username));
        return ResponseEntity.ok(new BearerToken(userService.newJwtToken(username), "Bearer "));
    }

}
