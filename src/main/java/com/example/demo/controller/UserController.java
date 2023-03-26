package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import lombok.AllArgsConstructor;
import com.example.demo.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {
    private UserMapper mapper;

    @PostMapping("/getAll")
    public List<User> getAll() {
        return mapper.selectAll();
    }
}