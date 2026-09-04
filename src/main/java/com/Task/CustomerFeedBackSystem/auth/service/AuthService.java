package com.Task.CustomerFeedBackSystem.auth.service;

import com.Task.CustomerFeedBackSystem.auth.dto.AuthResponse;
import com.Task.CustomerFeedBackSystem.auth.dto.SigninRequest;
import com.Task.CustomerFeedBackSystem.auth.dto.SignupRequest;
import com.Task.CustomerFeedBackSystem.auth.jwt.JwtService;
import com.Task.CustomerFeedBackSystem.exception.PasswordMismatchException;
import com.Task.CustomerFeedBackSystem.exception.ResourceNotFoundException;
import com.Task.CustomerFeedBackSystem.user.dto.UserResponse;
import com.Task.CustomerFeedBackSystem.user.entity.Role;
import com.Task.CustomerFeedBackSystem.user.entity.User;
import com.Task.CustomerFeedBackSystem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public UserResponse signUp(SignupRequest signupRequest) {
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
    }

   public AuthResponse signIn(SigninRequest signinRequest) {
        try {
            authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        User user = userRepository.findByUsername(signinRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + signinRequest.getUsername()));
        String token = jwtService.generateToken(signinRequest.getUsername(), user.getRole().name());
        return new AuthResponse(token);
   }

}
