package org.ryuu.learn.springsecurity.mapper;

import org.ryuu.learn.springsecurity.dto.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityMapper {
    int insert(Authority authority);

    List<Authority> selectByUsername(String username);

    List<Authority> selectByAuthority(String authority);

    int deleteByAuthority(Authority authority);

    int deleteByUsername(String username);
}
