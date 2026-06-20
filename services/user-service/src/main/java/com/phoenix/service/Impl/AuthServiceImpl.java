package com.phoenix.service.Impl;

import com.phoenix.enums.UserRole;
import com.phoenix.payload.dto.UserDTO;
import com.phoenix.payload.response.AuthResponse;
import com.phoenix.config.JwtProvider;
import com.phoenix.mapper.UserMapper;
import com.phoenix.model.User;
import com.phoenix.repository.UserRepository;
import com.phoenix.service.AuthService;
import com.phoenix.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    /*
    1. Check if user exists
    2. Encode password using BCrypt
    3. Save user in DB
    4. Generate JWT token
    5. Return JWT token and user details
     */

    @Override
    public AuthResponse signup(UserDTO req) throws Exception {
        User existingUser = userRepository.findByEmail(req.getEmail());

        if(existingUser != null){
            throw new Exception("User already exists");
        }
        if(req.getRole() == UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("You can not signup as system admin");
        }

        User newUser = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(req.getRole())
                .fullName(req.getFullName())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
                savedUser.getPassword());

        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(savedUser));
        authResponse.setTitle("Welcome "+savedUser.getFullName()+ ", to Phoenix Airline");
        authResponse.setMessage("You are registered successfully");
        return authResponse;
    }

    /*
        1.Load user from DB by email
        2. Compare password with BCrypt
        3. Update last-login time
        4. Generate JWT token
        5. Return JWT token and user details
     */

    @Override
    public AuthResponse login(String email, String password) throws Exception {

        Authentication authentication = authenticate(email, password);

        User user = userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String jwt = jwtProvider.generateToken(authentication, user.getId());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(user));
        authResponse.setTitle("Welcome Back "+user.getFullName()+ ", to Phoenix Airline");
        authResponse.setMessage("You are logged in successfully");

        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if(!passwordEncoder.matches(password,
                userDetails.getPassword())){
            throw new Exception("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
    }
}
