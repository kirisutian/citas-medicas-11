package com.christian.auth.services;

import com.christian.auth.dto.LoginRequest;
import com.christian.auth.dto.TokenResponse;

public interface AuthService {
    TokenResponse autenticar(LoginRequest request) throws Exception;
}