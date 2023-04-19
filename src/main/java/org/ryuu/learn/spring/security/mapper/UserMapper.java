package org.ryuu.learn.spring.security.mapper;

import org.ryuu.learn.spring.security.dto.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int insert(User user);

    List<User> selectAll();

    User selectByUsername(String username);

    int deleteByUsername(String username);
}
