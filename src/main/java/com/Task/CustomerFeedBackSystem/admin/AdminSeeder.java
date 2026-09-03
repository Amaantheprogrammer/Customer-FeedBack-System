package com.Task.CustomerFeedBackSystem.admin;

import com.Task.CustomerFeedBackSystem.user.entity.User;
import com.Task.CustomerFeedBackSystem.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AdminSeeder {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedAdmin() {
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            User admin = User.builder()
                    .username("Amaan")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .build();
            userRepository.save(admin);
        }
    }
}
