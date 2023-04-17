package org.ryuu.learn.springsecurity.service.impl;

import lombok.AllArgsConstructor;
import org.ryuu.learn.springsecurity.dto.Authority;
import org.ryuu.learn.springsecurity.mapper.AuthorityMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorityService {
    private final AuthorityMapper authorityMapper;

    int create(Authority authority) {
        try {
            return authorityMapper.insert(authority);
        } catch (DuplicateKeyException e) {
            return -1;
        }
    }

    public List<Authority> queryByUsername(String username) {
        return authorityMapper.selectByUsername(username);
    }

    public List<Authority> queryByAuthority(String authority) {
        return authorityMapper.selectByAuthority(authority);
    }

    public int deleteByAuthority(Authority authority) {
        return authorityMapper.deleteByAuthority(authority);
    }

    @Transactional
    public int deleteByUsername(String username) {
        return authorityMapper.deleteByUsername(username);
    }
}
