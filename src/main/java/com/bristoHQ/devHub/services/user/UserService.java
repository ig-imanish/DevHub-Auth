package com.bristoHQ.devHub.services.user;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bristoHQ.devHub.dto.auth.LoginDto;
import com.bristoHQ.devHub.dto.auth.RegisterDto;
import com.bristoHQ.devHub.dto.user.UserDTO;
import com.bristoHQ.devHub.dto.user.UserProfileUpdateDTO;
import com.bristoHQ.devHub.models.User;
import com.bristoHQ.devHub.models.role.Role;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

  String authenticate(LoginDto loginDto);

  ResponseEntity<?> register(RegisterDto registerDto);

  Role saveRole(Role role);

  UserDTO saveUser(User user);

  public UserDTO getUserDetails(String actualToken);

  public UserDTO findByEmail(String email);

  public UserDTO findByUsername(String username);

  List<UserDTO> getAllUsers();

  UserDTO findById(Long id);

  void blacklistToken(String token);

  boolean isTokenBlacklisted(String token);

  boolean isOAuthUser(String emailOrUsername);

  public boolean isUserPremiumByUsername(String username);

  public boolean isUserPremiumByEmail(String email);

  public boolean isUserPremiumByEmailOrUsername(String email, String username);

  public void logout(HttpServletRequest request);

  public UserDTO findByEmailOrUsername(String email, String username);

  public UserDTO saveUser(UserDTO user);

  public String newJwtToken(String emailOrUsername);

  public void updateUserDetails(String username, UserProfileUpdateDTO updatedUser);

  public UserDTO updateUsername(String oldUsername, String newUsername);

  public UserDTO updateEmail(String usernameOrEmail, String newEmail) ;

  public Role updateUsernameInRole(String username);

  public boolean isUserExist(String email);
}
