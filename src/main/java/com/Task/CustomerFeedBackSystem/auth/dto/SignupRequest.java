package com.Task.CustomerFeedBackSystem.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequest {
    @NotBlank(message = "Username is a required field")
    private String username;

    @NotBlank(message = "Email is a required field")
    private String email;

    @NotBlank(message = "Password is a required field")
    @Size(message = "Password must have at least 6 characters", min = 6)
    private String password;

    @NotBlank(message = "Re-enter your password")
    private String confirmPassword;
}
