package com.ecom.service.impl;

import com.ecom.dao.PasswordHistoryRepository;
import com.ecom.dao.RefreshTokenRepository;
import com.ecom.dao.UserRepository;
import com.ecom.dto.*;
import com.ecom.entity.PasswordEntityHistory;
import com.ecom.entity.Role;
import com.ecom.entity.User;
import com.ecom.exception.UserAlreadyExistsException;
import com.ecom.exception.UserNotFoundException;
import com.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    @Override
    @Transactional
    public User createUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("already this username exist");
        }


        String email = request.getEmail().toLowerCase().trim();
        Set<Role> roles = Set.of(Role.USER);

        User user = User.builder()
                .name(request.getName())
                .email(email)
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .tokenVersion(0)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User loadUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found "));
    }

    @Override
    public UserDto updateProfile(String email, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found with id"));
        user.setName(userUpdateRequest.getName());
        user.setPhone(userUpdateRequest.getPhone());
        User saved = userRepository.save(user);

        return UserDto.from(saved);
    }

    @Override
    @Transactional
    public void changePassword(String email, UpdatePassword updatePassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found"));

        if (!passwordEncoder.matches(updatePassword.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid old password");
        }
        if (passwordEncoder.matches(updatePassword.getNewPassword(), updatePassword.getOldPassword())) {
            throw new IllegalArgumentException("New password must be different from old password");
        }

        List<PasswordEntityHistory> passwordHistory=
                passwordHistoryRepository.findTop5ByUserOrderByCreatedAtDesc(user);

        for (PasswordEntityHistory history:passwordHistory){
            if (passwordEncoder.matches(updatePassword.getNewPassword(), history.getPasswordHash())) {
                throw new PasswordReuseException("Cannot reuse last 5 passwords");
            }
        }

        passwordHistoryRepository.save(
                PasswordEntityHistory.builder()
                        .user(user)
                        .passwordHash(user.getPassword())
                        .build()
        );
        user.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        userRepository.save(user);
        refreshTokenRepository.deleteAllByUser(user);
    }

    @Override
    public void deleteUserByEmail(String email) {
       User byEmail = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("user not found can't delete"));
        userRepository.delete(byEmail);
    }

    @Override
    public  List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public  UserDto updateRole(UpdateRole updateRole) {

      User user =userRepository.findById(updateRole.getUserId()).orElseThrow(()->new UserNotFoundException("user does not exist with this id please check id"));
      Set<Role> newRoles=updateRole.getRoles();
      Set<Role> oldRoles=user.getRoles();
      if(newRoles==null || newRoles.isEmpty() ){
          throw new IllegalArgumentException("roles can't be empty");
      }

      if(newRoles.equals(oldRoles)){
          throw new IllegalArgumentException("Old role and new roles cannot be same");
      }
        user.setRoles(newRoles);
        user.setTokenVersion(user.getTokenVersion()+1);
        userRepository.save(user);
        return UserDto.from(user);
    }

}
