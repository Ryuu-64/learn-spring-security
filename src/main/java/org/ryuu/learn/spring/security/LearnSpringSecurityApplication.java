package org.ryuu.learn.spring.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.ryuu.learn.spring.security.mapper")
@SpringBootApplication
public class LearnSpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearnSpringSecurityApplication.class, args);
    }
}
