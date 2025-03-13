package com.bristoHQ.devHub.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.bristoHQ.devHub.dto.MessageResponseDTO;
import com.bristoHQ.devHub.models.User;
import com.bristoHQ.devHub.models.premium.RedeemCode;
import com.bristoHQ.devHub.repositories.RedeemCodeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PremiumService {

    private UserServiceImpl userService;
    private RedeemCodeRepository redeemCodeRepository;

    public MessageResponseDTO redeemPremium(String code, String emailOrUsername) {

        User user = userService.findByEmailOrUsername(emailOrUsername, emailOrUsername);
        if (user == null) {
            return new MessageResponseDTO(false, "User not found", new Date());
        }
        if (user.isPremium()) {
            return new MessageResponseDTO(false, "User is already Premium", new Date());
        }

        RedeemCode redeemCode = redeemCodeRepository.findByRedeemCode(code);
        if (redeemCode == null) {
            return new MessageResponseDTO(false, "Invalid Redeem code", new Date());
        }
        if (redeemCode.isUsed()) {
            return new MessageResponseDTO(false, "Redeem code is already used by someone", new Date());
        }
        redeemCode.setUsed(true);
        redeemCode.setRedeemedBy(emailOrUsername);
        redeemCode.setRedeemedAt(new Date());
        redeemCodeRepository.save(redeemCode);

        user.setPremium(true);
        user.setRedeemCode(redeemCode);
        userService.saverUser(user);
        return new MessageResponseDTO(true, emailOrUsername + " have redeemed Premium successfully!", new Date());
    }
}
