package com.bristoHQ.devHub.services.email;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.bristoHQ.devHub.models.User;
import com.bristoHQ.devHub.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private static final long OTP_VALID_DURATION = 10; // 10 minutes

    public void generateAndSendOtp(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Generate OTP
            String otp = generateOTP();
            
            // Save OTP to user
            user.setOtp(otp);
            user.setOtpGeneratedTime(LocalDateTime.now());
            userRepository.save(user);
            
            // Send OTP via email
            emailService.sendOtpEmail(email, otp);
        } else {
            throw new RuntimeException("User with email " + email + " not found");
        }
    }

    public boolean verifyOtp(String email, String otp) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Check if OTP matches and is not expired
            if (user.getOtp().equals(otp) && 
                    LocalDateTime.now().isBefore(user.getOtpGeneratedTime().plusMinutes(OTP_VALID_DURATION))) {
                
                // Mark user as verified
                user.setVerified(true);
                user.setOtp(null);
                user.setOtpGeneratedTime(null);
                userRepository.save(user);
                
                return true;
            }
        }
        
        return false;
    }

    private String generateOTP() {
        // Generate 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
