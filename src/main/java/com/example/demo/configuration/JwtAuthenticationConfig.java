package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtAuthenticationConfig {
    @Value("${jwt.authorization.key}")
    private String authorizationKey;

    @Value("${jwt.authorization.valuePrefix}")
    private String authorizationValuePrefix;

    @Bean(name = "authorizationKey")
    public String getAuthorizationKey() {
        return authorizationKey;
    }

    @Bean(name = "authorizationValuePrefix")
    public String getAuthorizationValuePrefix() {
        return authorizationValuePrefix;
    }
}
