package com.ecom.service;

import com.ecom.dto.AuthResponse;
import com.ecom.dto.LoginRequest;
import com.ecom.dto.RegisterRequest;


public interface AuthService {

    AuthResponse registerUser(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
}
