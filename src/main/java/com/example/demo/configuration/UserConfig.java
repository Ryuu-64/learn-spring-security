package com.example.demo.configuration;

import com.example.demo.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@AllArgsConstructor
public class UserConfig {
    private final UserMapper userMapper;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User userDetails = User
                    .builder()
                    .username(username)
                    .build();
            User user = userMapper.selectOne(userDetails);
            if (user == null) {
                throw new UsernameNotFoundException("username not found");
            }
            return user;
        };
    }
}
