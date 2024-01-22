package com.notesapp.backend.entities.invalidtoken.interfaces;


import com.notesapp.backend.entities.invalidtoken.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {

    List<InvalidToken> getAllByExpirationDateBefore(Instant currentTime);

    boolean existsByToken(String token);
}
