package org.ryuu.learn.springsecurity.service.impl;

import lombok.AllArgsConstructor;
import org.ryuu.learn.springsecurity.dto.User;
import org.ryuu.learn.springsecurity.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserMapper userMapper;

    private final AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = queryByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        return user;
    }

    public int create(User user) {
        return userMapper.insert(user);
    }

    public List<User> queryAll() {
        return userMapper.selectAll();
    }

    public User queryByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Transactional
    public int deleteUserWithAuthorities(String username) {
        int rowsAffected = userMapper.deleteByUsername(username);
        authorityService.deleteByUsername(username);
        return rowsAffected;
    }
}
