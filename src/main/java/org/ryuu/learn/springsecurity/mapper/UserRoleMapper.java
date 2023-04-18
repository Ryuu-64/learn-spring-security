package org.ryuu.learn.springsecurity.mapper;

import org.ryuu.learn.springsecurity.dto.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    int insert(UserRole userRole);

    List<UserRole> selectByUsername(String username);

    List<UserRole> selectByRole(String role);

    int update(UserRole userRole);

    int deleteByRole(UserRole userRole);

    int deleteByUsername(String username);
}
