package org.ryuu.learn.springsecurity.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    private final String signingKey;

    private final int expiresIn;

    private final TimeUnit expiresInTimeUnit;

    private final ObjectMapper objectMapper;

    public JwtService(
            @Value("${jwt.signing-key}") String signingKey,
            @Value("${jwt.expires-in}") int expiresIn,
            @Value("${jwt.expires-in-unit}") TimeUnit expiresInTimeUnit,
            ObjectMapper objectMapper
    ) {
        this.signingKey = signingKey;
        this.expiresIn = expiresIn;
        this.expiresInTimeUnit = expiresInTimeUnit;
        this.objectMapper = objectMapper;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String usernameInToken = getSubject(token);
        String username = userDetails.getUsername();
        return usernameInToken.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getToken(String subject) {
        return getToken(subject, new HashMap<>());
    }

    private String getToken(String subject, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiresInTimeUnit.toMillis(expiresIn)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .serializeToJsonWith(new JacksonSerializer<>(objectMapper))
                .compact();
    }

    private Key getSigningKey() {
        try {
            byte[] keyBytes = signingKey.getBytes(StandardCharsets.UTF_8);
            keyBytes = MessageDigest.getInstance("SHA-256").digest(keyBytes);
            String base64Key = Base64.getUrlEncoder().encodeToString(keyBytes);
            keyBytes = Decoders.BASE64URL.decode(base64Key);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
