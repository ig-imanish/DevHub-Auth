package com.bristoHQ.devHub.controllers.auth;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bristoHQ.devHub.dto.MessageResponseDTO;
import com.bristoHQ.devHub.dto.auth.BearerToken;
import com.bristoHQ.devHub.dto.auth.LoginDto;
import com.bristoHQ.devHub.dto.auth.RegisterDto;
import com.bristoHQ.devHub.dto.otp.ResendOtpRequest;
import com.bristoHQ.devHub.dto.otp.VerifyOtpRequest;
import com.bristoHQ.devHub.dto.user.UserDTO;
import com.bristoHQ.devHub.security.jwt.JwtUtilities;
import com.bristoHQ.devHub.services.email.EmailOtpService;
import com.bristoHQ.devHub.services.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthRestController {

    private final UserService userService;
    private final JwtUtilities jwtUtilities;
    private final EmailOtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> register(@RequestBody RegisterDto registerDto) {
        
        userService.register(registerDto);
        otpService.generateAndSendOtp(registerDto.getEmail());

        return ResponseEntity.ok(new MessageResponseDTO(true, "User registered successfully. Please check your email for OTP verification.", new Date()));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<MessageResponseDTO> verifyOtp(@RequestBody VerifyOtpRequest request) {
        boolean verified = otpService.verifyOtp(request.getEmail(), request.getOtp());
        
        if (verified) {
            return ResponseEntity.ok(new MessageResponseDTO(true, "Email verified successfully", new Date()));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(false, "Invalid OTP or OTP expired", new Date()));
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<MessageResponseDTO> resendOtp(@RequestBody ResendOtpRequest request) {
        try {
            // UserDTO userDTO = userService.findByEmail(request.getEmail());
            // if(userDTO.isVerified()) {
            //     return ResponseEntity.badRequest().body(new MessageResponseDTO(false, "Email already verified", new Date()));
            // }
            otpService.generateAndSendOtp(request.getEmail());
            return ResponseEntity.ok(new MessageResponseDTO(true, "OTP sent to your email", new Date()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO(false, e.getMessage(), new Date()));
        }
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
