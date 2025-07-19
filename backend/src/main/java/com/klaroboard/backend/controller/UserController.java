package com.klaroboard.backend.controller;

import com.klaroboard.backend.dto.UserRequestDTO;
import com.klaroboard.backend.dto.UserResponseDTO;
import com.klaroboard.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Combination of @Controller and @ResponseBody. Creates RESTful endpoints.
@RequestMapping("/api/users") // All endpoints in this class will start with /api/users
@CrossOrigin(origins = "*") // Allows requests from any origin (e.g., our future React app)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}