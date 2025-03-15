package com.bristoHQ.devHub.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bristoHQ.devHub.dto.UserDTO;
import com.bristoHQ.devHub.models.User;

@Service
public class DTOService {
    
    public UserDTO convertUserToUserDTO(User user) {

        if(user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();

        if(user.getId() != null) {
            userDTO.setId(user.getId());
        }
        if(user.getFullName() != null) {
            userDTO.setFullName(user.getFullName());
        }
        if(user.getUsername() != null) {
            userDTO.setUsername(user.getUsername());
        }
        if(user.getEmail() != null) {
            userDTO.setEmail(user.getEmail());
        }
        if(user.getRoles() != null) {
            userDTO.setRoles(user.getRoles());
        }
        if(!user.getProvider().isEmpty()){
            userDTO.setProvider(user.getProvider());
        }
        if(user.getRedeemCode() != null) {
            userDTO.setRedeemCode(user.getRedeemCode());
        }
        if(!user.getRoles().isEmpty()) {
            userDTO.setRoles(user.getRoles());
        }
        if(user.getAccountCreatedAt() != null) {
            userDTO.setAccountCreatedAt(user.getAccountCreatedAt());
        }
        if (user.getUserAvatar() != null) {
            userDTO.setUserAvatar(user.getUserAvatar());
        }
        
        if (user.getUserAvatarpublicId() != null) {
            userDTO.setUserAvatarpublicId(user.getUserAvatarpublicId());
        }
        
        if (user.getUserBanner() != null) {
            userDTO.setUserBanner(user.getUserBanner());
        }
        
        if (user.getUserBannerpublicId() != null) {
            userDTO.setUserBannerpublicId(user.getUserBannerpublicId());
        }
        
        return userDTO;
    }

    public User convertUserDTOToUser(UserDTO userDTO){
        if(userDTO == null) {
            return null;
        }
        User user = new User();

        if(userDTO.getId() != null) {
            user.setId(userDTO.getId());
        }
        if(userDTO.getFullName() != null) {
            user.setFullName(userDTO.getFullName());
        }
        if(userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if(userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if(userDTO.getRoles() != null) {
            user.setRoles(userDTO.getRoles());
        }
        if(!userDTO.getProvider().isEmpty()){
            user.setProvider(userDTO.getProvider());
        }
        if(userDTO.getRedeemCode() != null) {
            user.setRedeemCode(userDTO.getRedeemCode());
        }
        if(!userDTO.getRoles().isEmpty()) {
            user.setRoles(userDTO.getRoles());
        }
        if(userDTO.getAccountCreatedAt() != null) {
            user.setAccountCreatedAt(userDTO.getAccountCreatedAt());
        }

        return user;
    }

    public List<UserDTO> convertUserToUserDTO(List<User> all) {
        if(all != null){
            return null;
        }
        for(User user : all) {
            return List.of(convertUserToUserDTO(user));
        }
        return null;
    }
}
