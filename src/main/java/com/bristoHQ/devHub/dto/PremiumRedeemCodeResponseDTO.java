package com.bristoHQ.devHub.dto;

import java.util.Date;

import com.bristoHQ.devHub.models.premium.RedeemCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PremiumRedeemCodeResponseDTO {
    private String id;
    private String redeemCode;
    private boolean isUsed;
    private String redeemedBy;
    private Date redeemedAt;
    private Date createdAt;

    public PremiumRedeemCodeResponseDTO premiumRedeemCodeResponseDTOFromRedeemCode(RedeemCode redeemCode){
        return new PremiumRedeemCodeResponseDTO(redeemCode.getId(), redeemCode.getRedeemCode(), redeemCode.isUsed(), redeemCode.getRedeemedBy(), redeemCode.getRedeemedAt(), redeemCode.getCreatedAt());
    }
}
