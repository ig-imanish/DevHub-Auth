package com.bristoHQ.devHub.models.premium;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RedeemCode {
    @Id
    private String id;
    private String redeemCode;
    private boolean isUsed;
    private String redeemedBy;
    private Date redeemedAt;
    private Date createdAt;
}
