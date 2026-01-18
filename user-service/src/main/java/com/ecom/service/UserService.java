package com.ecom.service;

import com.ecom.dto.*;
import com.ecom.entity.User;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface UserService {
    User createUser(RegisterRequest request);
    User loadUserByEmail(String email);

    UserDto updateProfile(String email, UserUpdateRequest userUpdateRequest);

    void changePassword(String email,  UpdatePassword updatePassword);

    void deleteUserByEmail(String email);

     List<User> findAll();

     UserDto updateRole(UpdateRole updateRole);
}
