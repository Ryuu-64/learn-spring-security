package org.ryuu.learn.springsecurity.dto;

import lombok.Data;

@Data
public class Authority {
    private String username;
    private String authority;
}
