package com.phoenix.service;

import com.phoenix.payload.dto.UserDTO;
import com.phoenix.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password) throws Exception;
    AuthResponse signup(UserDTO req) throws Exception;
}
