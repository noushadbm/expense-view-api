package com.rayshan.expenseview.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class PasswordService {
    private static final int BCRYPT_STRENGTH = 12;

    private final BCryptPasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom;

    private String jwtSecret = "your-super-secret-jwt-key-change-this-in-production";
    private int defaultExpiryHours = 24;

    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder(BCRYPT_STRENGTH);
        this.secureRandom = new SecureRandom();
    }

    /**
     * Encrypts a password using BCrypt
     * @param password Plain text password
     * @return Hashed password
     * @throws IllegalArgumentException if password is null or empty
     */
    public String encrypt(String password) {
        //validatePassword(password);
        return passwordEncoder.encode(password);
    }

    /**
     * Verifies if a password matches the hashed version
     * @param password Plain text password
     * @param hashedPassword Hashed password
     * @return true if password matches, false otherwise
     */
    public boolean isCorrectPassword(String password, String hashedPassword) {
        if (password == null || hashedPassword == null) {
            return false;
        }

        try {
            return passwordEncoder.matches(password, hashedPassword);
        } catch (Exception e) {
            System.err.println("Password comparison error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates a JWT token with claims and expiry
     * @param claims Token payload as Map
     * @param expiryInstant Custom expiry time (optional)
     * @return JWT token string
     * @throws IllegalArgumentException if claims is null
     */
    public String generateJwt(Map<String, Object> claims, Instant expiryInstant) {
        if (claims == null) {
            throw new IllegalArgumentException("Claims cannot be null");
        }

        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Instant expiry = expiryInstant != null ? expiryInstant : getTokenExpiry();

            JwtBuilder builder = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(expiry))
                    .signWith(key, SignatureAlgorithm.HS256);

            return builder.compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT: " + e.getMessage(), e);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

    /**
     * Verifies a JWT token and returns claims
     * @param token JWT token to verify
     * @return Claims from the token
     * @throws RuntimeException if token is invalid or expired
     */
    public Claims verifyJwt(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }

        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired: " + e.getMessage(), e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT token: " + e.getMessage(), e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Malformed JWT token: " + e.getMessage(), e);
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT token compact of handler are invalid: " + e.getMessage(), e);
        }
    }

    /**
     * Gets default token expiry time (24 hours)
     * @return Expiry Instant
     */
    public Instant getTokenExpiry() {
        return getTokenExpiry(defaultExpiryHours);
    }

    /**
     * Gets default token expiry time
     * @param hours Hours from now
     * @return Expiry Instant
     */
    public Instant getTokenExpiry(int hours) {
        return Instant.now().plus(hours, ChronoUnit.HOURS);
    }
}
