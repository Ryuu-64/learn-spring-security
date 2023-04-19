package org.ryuu.learn.spring.security.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Authority implements GrantedAuthority {
    private String authority;
}
