package org.ryuu.learn.springsecurity.configuration;

import org.ryuu.learn.springsecurity.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.ryuu.learn.springsecurity.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
