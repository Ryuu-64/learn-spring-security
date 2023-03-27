package com.example.demo.service.impl;

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
import java.util.function.Function;

@Service
public class JwtService {
    private final String signingKey;

    private final ObjectMapper objectMapper;

    public JwtService(
            @Value("${jwt.signing-key}") String signingKey,
            ObjectMapper objectMapper
    ) {
        this.signingKey = signingKey;
        this.objectMapper = objectMapper;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String usernameInToken = extractUsername(token);
        String username = userDetails.getUsername();
        return usernameInToken.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(25)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .serializeToJsonWith(new JacksonSerializer<>(objectMapper))
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                // TODO catch SignatureException
                //  SignatureException: JWT signature does not match locally computed signature.
                //  JWT validity cannot be asserted and should not be trusted.
                .parseClaimsJws(token)
                .getBody();
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
