package org.ryuu.learn.spring.security.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class UserRole implements GrantedAuthority {
    private String username;
    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
