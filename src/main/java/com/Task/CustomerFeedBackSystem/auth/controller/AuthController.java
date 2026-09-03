package com.Task.CustomerFeedBackSystem.auth.controller;

import com.Task.CustomerFeedBackSystem.auth.dto.AuthResponse;
import com.Task.CustomerFeedBackSystem.auth.dto.SigninRequest;
import com.Task.CustomerFeedBackSystem.auth.dto.SignupRequest;
import com.Task.CustomerFeedBackSystem.auth.service.AuthService;
import com.Task.CustomerFeedBackSystem.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> signUp(@RequestBody @Valid SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.signUp(signupRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody @Valid SigninRequest signipRequest) {
        return ResponseEntity.ok(authService.signIn(signipRequest));
    }
}
