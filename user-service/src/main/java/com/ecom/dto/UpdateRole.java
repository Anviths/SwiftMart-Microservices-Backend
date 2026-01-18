package com.ecom.dto;

import com.ecom.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateRole {
    private long userId;
    private Set<Role> roles;
}
