package com.notesapp.backend.config.jwt;

import com.notesapp.backend.config.jwt.interfaces.JwtService;
import com.notesapp.backend.utils.exceptions.auth.InvalidJWTException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "4265495A4E6C62614F346C6E526D4F736F47364571654A71666E4F764B4E6738";
    private static final long EXPIRATION_TIME = 1000L * 60L * 120L;

    public final String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public final String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public final Instant extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration).toInstant();
    }

    public final boolean isTokenValid(String token, UserDetails userDetails) {
        log.info("Validating token for username: {}", userDetails.getUsername());

        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        long currentTime = System.currentTimeMillis();

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ignored) {
            throw new InvalidJWTException("Token has expired");
        } catch (MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException ignored) {
            throw new InvalidJWTException("Token is invalid");
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
