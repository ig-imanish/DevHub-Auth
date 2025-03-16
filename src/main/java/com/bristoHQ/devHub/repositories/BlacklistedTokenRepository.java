package com.bristoHQ.devHub.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bristoHQ.devHub.models.auth.BlacklistedToken;

public interface BlacklistedTokenRepository extends MongoRepository<BlacklistedToken, String> {
    boolean existsByToken(String token);
}
