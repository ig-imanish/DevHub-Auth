package com.bristoHQ.devHub.scheduling;

import com.bristoHQ.devHub.repositories.BlacklistedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Runs at midnight every day
    public void cleanupExpiredTokens() {
        blacklistedTokenRepository.deleteAll(
                blacklistedTokenRepository.findAll().stream()
                        .filter(token -> token.getExpirationDate().isBefore(Instant.now()))
                        .toList());
    }
}
