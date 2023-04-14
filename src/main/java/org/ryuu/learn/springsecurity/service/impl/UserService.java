package org.ryuu.learn.springsecurity.service.impl;

import lombok.AllArgsConstructor;
import org.ryuu.learn.springsecurity.dto.User;
import org.ryuu.learn.springsecurity.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public int create(User user) {
        return userMapper.insert(user);
    }

    public List<User> queryAll() {
        return userMapper.selectAll();
    }

    public User queryByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public int deleteByUsername(String username) {
        return userMapper.deleteByUsername(username);
    }
}
