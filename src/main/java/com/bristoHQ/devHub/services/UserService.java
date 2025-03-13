package com.bristoHQ.devHub.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bristoHQ.devHub.dto.LoginDto;
import com.bristoHQ.devHub.dto.RegisterDto;
import com.bristoHQ.devHub.models.User;
import com.bristoHQ.devHub.models.role.Role;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

  String authenticate(LoginDto loginDto);

  ResponseEntity<?> register(RegisterDto registerDto);

  Role saveRole(Role role);

  User saverUser(User user);

  public User getUserDetails(String actualToken);

  public User findByEmail(String email);

  public User findByUsername(String username);

  List<User> getAllUsers();

  User findById(Long id);

  void blacklistToken(String token);

  boolean isTokenBlacklisted(String token);

  boolean isOAuthUser(String emailOrUsername);

  public boolean isUserPremiumByUsername(String username);

  public boolean isUserPremiumByEmail(String email);

  public boolean isUserPremiumByEmailOrUsername(String email, String username);

  public void logout(HttpServletRequest request);

  public User findByEmailOrUsername(String email, String username);

}
