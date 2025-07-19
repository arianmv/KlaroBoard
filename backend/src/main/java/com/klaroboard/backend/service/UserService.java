package com.klaroboard.backend.service;

import com.klaroboard.backend.dto.UserRequestDTO;
import com.klaroboard.backend.dto.UserResponseDTO;
import com.klaroboard.backend.model.User;
import com.klaroboard.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Marks this as a Spring service bean
public class UserService {

    private final UserRepository userRepository;

    // Spring automatically injects the UserRepository bean here (Dependency Injection)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Business logic to create a user
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());

        User savedUser = userRepository.save(user);

        return convertToResponseDTO(savedUser);
    }

    // Business logic to get all users
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert a User entity to a UserResponseDTO
    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}