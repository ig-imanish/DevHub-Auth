package com.bristoHQ.devHub.controllers.premium;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bristoHQ.devHub.dto.PremiumRedeemCodeResponseDTO;
import com.bristoHQ.devHub.helper.GenRedeemCode;
import com.bristoHQ.devHub.models.premium.RedeemCode;
import com.bristoHQ.devHub.repositories.RedeemCodeRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/premium")
@AllArgsConstructor
public class PremiumRestController {

    private final RedeemCodeRepository redeemCodeRepository;

    @PostMapping("/generate")
    public PremiumRedeemCodeResponseDTO generateRedeemCode() {
        String code = GenRedeemCode.generateRandomCode();
        RedeemCode redeemCode = new RedeemCode();
        redeemCode.setRedeemCode(code);
        redeemCode.setUsed(false);
        redeemCode.setCreatedAt(new Date());
        redeemCodeRepository.save(redeemCode);
        return new PremiumRedeemCodeResponseDTO().premiumRedeemCodeResponseDTOFromRedeemCode(redeemCode);
    }

    @GetMapping("/code/{code}")
    public PremiumRedeemCodeResponseDTO getRedeemCode(@PathVariable String code) {
        RedeemCode redeemCode = redeemCodeRepository.findByRedeemCode(code);
        return new PremiumRedeemCodeResponseDTO().premiumRedeemCodeResponseDTOFromRedeemCode(redeemCode);
    }

    @GetMapping("/code")
    public List<PremiumRedeemCodeResponseDTO> getAllRedeemCode() {
        List<RedeemCode> redeemCodes = redeemCodeRepository.findAll();
        return redeemCodes.stream()
                .map(redeemCode -> new PremiumRedeemCodeResponseDTO().premiumRedeemCodeResponseDTOFromRedeemCode(redeemCode))
                .collect(Collectors.toList());
    }
}
