package org.ryuu.learn.spring.security.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoder() {
        String rawPassword = "foo";
        String encodePassword1 = passwordEncoder.encode(rawPassword);
        String encodePassword2 = passwordEncoder.encode(rawPassword);
        assertNotEquals(encodePassword1, encodePassword2);

        boolean matches1 = passwordEncoder.matches(rawPassword, encodePassword1);
        boolean matches2 = passwordEncoder.matches(rawPassword, encodePassword2);
        assertTrue(matches1);
        assertTrue(matches2);
    }
}
