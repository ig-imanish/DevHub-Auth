package com.bristoHQ.devHub.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bristoHQ.devHub.dto.BearerToken;
import com.bristoHQ.devHub.dto.LoginDto;
import com.bristoHQ.devHub.dto.RegisterDto;
import com.bristoHQ.devHub.models.BlacklistedToken;
import com.bristoHQ.devHub.models.User;
import com.bristoHQ.devHub.models.role.Role;
import com.bristoHQ.devHub.models.role.RoleName;
import com.bristoHQ.devHub.repositories.BlacklistedTokenRepository;
import com.bristoHQ.devHub.repositories.RoleRepository;
import com.bristoHQ.devHub.repositories.UserRepository;
import com.bristoHQ.devHub.security.JwtUtilities;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository iUserRepository;
    @Autowired
    private RoleRepository iRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    public Role saveRole(Role role) {
        return iRoleRepository.save(role);
    }

    @Override
    public User saverUser(User user) {
        return iUserRepository.save(user);
    }

    // @Override
    // public ResponseEntity<?> register(RegisterDto registerDto) {
    // if (iUserRepository.existsByEmail(registerDto.getEmail())) {
    // return new ResponseEntity<>("email is already taken !",
    // HttpStatus.SEE_OTHER);
    // }
    // if (iUserRepository.existsByUsername(registerDto.getUsername())) {
    // return new ResponseEntity<>("username is already taken !",
    // HttpStatus.SEE_OTHER);
    // }
    // if (registerDto.getUsername().length() < 4 ||
    // registerDto.getUsername().length() > 20) {
    // return new ResponseEntity<>("username must be between 4 and 20 characters",
    // HttpStatus.BAD_REQUEST);
    // }
    // if (registerDto.getPassword().length() < 6 ||
    // registerDto.getPassword().length() > 20) {
    // return new ResponseEntity<>("password must be between 6 and 20 characters",
    // HttpStatus.BAD_REQUEST);
    // }
    // if (registerDto.getFullName().length() < 4 ||
    // registerDto.getFullName().length() > 20) {
    // return new ResponseEntity<>("full name must be between 4 and 20 characters",
    // HttpStatus.BAD_REQUEST);
    // }
    // if (registerDto.getEmail().length() < 4 || registerDto.getEmail().length() >
    // 20) {
    // return new ResponseEntity<>("email must be between 4 and 20 characters",
    // HttpStatus.BAD_REQUEST);
    // }
    // if (!registerDto.getUsername().startsWith("@")) {
    // registerDto.setUsername("@" + registerDto.getUsername());
    // }

    // User user = new User();
    // user.setEmail(registerDto.getEmail());
    // user.setFullName(registerDto.getFullName());
    // user.setUsername(registerDto.getUsername()); // Fixed the incorrect
    // assignment
    // user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

    // // Assign role with username
    // Role role = new Role(RoleName.USER, user.getUsername());
    // iRoleRepository.save(role); // Save role with username

    // user.setRoles(Collections.singletonList(role));
    // iUserRepository.save(user);

    // System.out.println("saved user: " + user);
    // String token = jwtUtilities.generateToken(registerDto.getEmail(),
    // Collections.singletonList(role.getRoleName()));
    // return new ResponseEntity<>(new BearerToken(token, "Bearer "),
    // HttpStatus.OK);

    // }

    @Override
    public ResponseEntity<?> register(RegisterDto registerDto) {
        System.out.println("Register method called with: " + registerDto);

        if (iUserRepository.existsByEmail(registerDto.getEmail())) {
            System.out.println("User with email already exists: " + registerDto.getEmail());
            return new ResponseEntity<>("Email is already taken!", HttpStatus.SEE_OTHER);
        }
        if (iUserRepository.existsByUsername(registerDto.getUsername())) {
            System.out.println("Username already exists: " + registerDto.getUsername());
            return new ResponseEntity<>("Username is already taken!", HttpStatus.SEE_OTHER);
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFullName(registerDto.getFullName());
        user.setUsername(registerDto.getUsername());
        user.setProvider(registerDto.getProvider());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Assign role with username
        Role role = new Role(RoleName.USER, user.getUsername());
        System.out.println("Saving role: " + role);
        iRoleRepository.save(role);

        user.setRoles(Collections.singletonList(role));
        System.out.println("Saving user: " + user);
        iUserRepository.save(user);

        System.out.println("User saved successfully: " + user);

        String token = jwtUtilities.generateToken(registerDto.getEmail(),
                Collections.singletonList(role.getRoleName()));
        return new ResponseEntity<>(new BearerToken(token, "Bearer "), HttpStatus.OK);
    }

    @Override
    public String authenticate(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmailOrUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = iUserRepository.findByEmailOrUsername(authentication.getName(), authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r -> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(user.getUsername(), rolesNames);
        return token;
    }

    @Override
    public User getUserDetails(String actualToken) {
        String email = jwtUtilities.extractUsername(actualToken);
        return iUserRepository.findByEmail(email).isPresent() ? iUserRepository.findByEmail(email).get() : null;
    }

    @Override
    public User findByEmail(String email) {
        return iUserRepository.findByEmail(email).isPresent() ? iUserRepository.findByEmail(email).get() : null;
    }

    @Override
    public User findByUsername(String username) {
        return iUserRepository.findByUsername(username).isPresent() ? iUserRepository.findByUsername(username).get()
                : null;
    }

    @Override
    public User findByEmailOrUsername(String email, String username) {
        return iUserRepository.findByEmailOrUsername(email, username).isPresent()
                ? iUserRepository.findByEmailOrUsername(email, username).get()
                : null;
    }

    @Override
    public List<User> getAllUsers() {
        if (iUserRepository.findAll().isEmpty()) {
            return null;
        }
        return iUserRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return iUserRepository.findById(id) == null ? null : iUserRepository.findById(id);
    }

    @Override
    public void logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
    }

    @Override
    public void blacklistToken(String token) {
        Claims claims = jwtUtilities.extractAllClaims(token);
        Instant expiration = claims.getExpiration().toInstant();
        BlacklistedToken blacklistedToken = new BlacklistedToken(token, expiration);
        blacklistedTokenRepository.save(blacklistedToken);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }

    @Override
    public boolean isUserPremiumByUsername(String username) {
        return iUserRepository.findByUsernameAndIsPremium(username, true).isPresent();
    }

    @Override
    public boolean isUserPremiumByEmail(String email) {
        return iUserRepository.findByEmailAndIsPremium(email, true).isPresent();
    }

    @Override
    public boolean isUserPremiumByEmailOrUsername(String email, String username) {
        return iUserRepository.findByEmailOrUsernameAndIsPremium(email, username, true).isPresent();
    }

    @Override
    public boolean isOAuthUser(String emailOrUsername) {
        Optional<User> user = iUserRepository.findByEmailOrUsername(emailOrUsername, emailOrUsername);
        if (user.get().getProvider().equalsIgnoreCase("LOCAL")) {
            return false;
        }

        if (user.get().getProvider().equalsIgnoreCase("GOOGLE")) {
            return true;
        }
        return false;

    }
}
