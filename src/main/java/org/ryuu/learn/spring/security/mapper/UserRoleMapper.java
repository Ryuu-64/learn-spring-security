package org.ryuu.learn.spring.security.mapper;

import org.ryuu.learn.spring.security.dto.UserRole;
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
