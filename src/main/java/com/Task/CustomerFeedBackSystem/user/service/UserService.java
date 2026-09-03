package com.Task.CustomerFeedBackSystem.user.service;

import com.Task.CustomerFeedBackSystem.exception.AccessDeniedException;
import com.Task.CustomerFeedBackSystem.exception.ResourceNotFoundException;
import com.Task.CustomerFeedBackSystem.user.dto.UserRequest;
import com.Task.CustomerFeedBackSystem.user.dto.UserResponse;
import com.Task.CustomerFeedBackSystem.user.entity.User;
import com.Task.CustomerFeedBackSystem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + id));
        return modelMapper.map(user, UserResponse.class);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + username));
        return modelMapper.map(user, UserResponse.class);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + id));
        if (!user.getUsername().equals(getCurrentUser().getUsername())) {
            throw new AccessDeniedException("Cannot acces other user accounts");
        }
        if (userRequest.getUsername() != null) {
            user.setUsername(userRequest.getUsername());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        User savedUser = userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + username));
    }
}
