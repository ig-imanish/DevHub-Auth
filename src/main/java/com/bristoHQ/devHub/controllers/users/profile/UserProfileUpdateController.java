package com.bristoHQ.devHub.controllers.users.profile;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bristoHQ.devHub.dto.MessageResponseDTO;
import com.bristoHQ.devHub.dto.auth.BearerToken;
import com.bristoHQ.devHub.dto.user.UserDTO;
import com.bristoHQ.devHub.dto.user.UserProfileUpdateDTO;
import com.bristoHQ.devHub.services.storage.CloudinaryService;
import com.bristoHQ.devHub.services.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users/profile/update")
@RequiredArgsConstructor
public class UserProfileUpdateController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @SuppressWarnings("rawtypes")
    @PutMapping()
    public ResponseEntity<MessageResponseDTO> postMethodName(@ModelAttribute UserProfileUpdateDTO user, @RequestParam(required = false) MultipartFile banner,
            @RequestParam(required = false) MultipartFile avatar, Principal principal) throws IOException {
        System.out.println("hello");
        if (principal == null) {
            return ResponseEntity.status(401).body(new MessageResponseDTO(false, "Unauthorized: No user logged in", new Date()));
        }
        String username = principal.getName();
        System.out.println(username);
        
        if (avatar != null) {
            try {
                Map uploadResultForAvatar = cloudinaryService.uploadImage(avatar);
                String imageUrlForAvatar = uploadResultForAvatar.get("url").toString();
                String publicIdForAvatar = uploadResultForAvatar.get("public_id").toString();
                user.setUserAvatar(imageUrlForAvatar);
                user.setUserAvatarpublicId(publicIdForAvatar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (banner != null) {
            try {
                Map uploadResultForBanner = cloudinaryService.uploadImage(avatar);
                String imageUrlForbanner = uploadResultForBanner.get("url").toString();
                String publicIdForBanner = uploadResultForBanner.get("public_id").toString();
                user.setUserAvatar(imageUrlForbanner);
                user.setUserAvatarpublicId(publicIdForBanner);

            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        userService.updateUserDetails(username, user);
        System.out.println("Uploaded " + user);
        return ResponseEntity.ok(new MessageResponseDTO(true, "Profile updated successfully", new Date()));
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestParam String email, Principal principal) {
        UserDTO user = userService.findByEmailOrUsername(principal.getName(), principal.getName());
        if(user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        if(user.getEmail().equalsIgnoreCase(email)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email is same as current email");
        }
        UserDTO alreadyExistUserByThisEmail = userService.findByEmail(email);
        if(alreadyExistUserByThisEmail != null){
            return ResponseEntity.status(HttpStatus.IM_USED).body("Email already exists");
        }
        System.out.println(userService.updateEmail(principal.getName(), email));
        return ResponseEntity.ok(new BearerToken(userService.newJwtToken(email), "Bearer "));
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
       System.out.println(userService.updateUsername(principal.getName(), username));
        return ResponseEntity.ok(new BearerToken(userService.newJwtToken(username), "Bearer "));
    }
}
