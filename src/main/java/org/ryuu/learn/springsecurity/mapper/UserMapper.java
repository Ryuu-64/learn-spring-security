package org.ryuu.learn.springsecurity.mapper;

import org.ryuu.learn.springsecurity.dto.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int insert(User user);

    List<User> selectAll();

    User selectByUsername(String username);

    int deleteByUsername(String username);
}
