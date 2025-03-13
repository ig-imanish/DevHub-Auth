package com.bristoHQ.devHub.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Data
@Document(collection = "blacklisted_tokens")
public class BlacklistedToken {
    @Id
    private String token;
    private Instant expirationDate;

    public BlacklistedToken(String token, Instant expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }
}
