package com.example.demo.mapper;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> selectAll();

    User selectOne(User user);

    void insert(User user);
}
