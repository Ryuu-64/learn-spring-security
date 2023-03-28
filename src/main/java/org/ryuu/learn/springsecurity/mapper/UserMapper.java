package org.ryuu.learn.springsecurity.mapper;

import org.ryuu.learn.springsecurity.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> selectAll();

    User selectOne(User user);

    void insert(User user);
}
