package com.notesapp.backend.utils.invalidtoken;

import com.notesapp.backend.entities.invalidtoken.InvalidToken;
import com.notesapp.backend.entities.invalidtoken.interfaces.InvalidTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCleanup {

    private final InvalidTokenRepository invalidTokenRepository;

    @Scheduled(fixedRate = 1000L*60L*2L)
    public void cleanupInvalidTokens() {
        log.info("Cleaning expired tokens");
        List<InvalidToken> invalidTokens = invalidTokenRepository.getAllByExpirationDateBefore(Instant.now());
        log.info("Deleting {} tokens", invalidTokens.size());
        invalidTokenRepository.deleteAll(invalidTokens);
    }
}
