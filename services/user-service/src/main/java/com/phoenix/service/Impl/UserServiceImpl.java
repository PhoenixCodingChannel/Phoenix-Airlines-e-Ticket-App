package com.phoenix.service.Impl;

import com.phoenix.mapper.UserMapper;
import com.phoenix.model.User;
import com.phoenix.payload.dto.UserDTO;
import com.phoenix.repository.UserRepository;
import com.phoenix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found");
        }
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                () -> new Exception("User not found with id: " + id)
        );
        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users= userRepository.findAll();
        return UserMapper.toDTO(users);
    }
}
