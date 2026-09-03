package com.Task.CustomerFeedBackSystem.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
