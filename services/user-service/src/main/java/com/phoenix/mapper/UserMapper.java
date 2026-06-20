package com.phoenix.mapper;

import com.phoenix.payload.dto.UserDTO;
import com.phoenix.model.User;

import java.util.List;

public class UserMapper {

    public static UserDTO toDTO(User user){
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .phone(user.getPhone())
                .lastLogin(user.getLastLogin())
                .build();
    }

    public static List<UserDTO> toDTO(List<User> users){
        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}
