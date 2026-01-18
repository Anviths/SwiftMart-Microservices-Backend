package com.ecom.dto;

import com.ecom.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String refreshToken;
    private String email;
    private String name;
    private Set<Role> roles;
}
