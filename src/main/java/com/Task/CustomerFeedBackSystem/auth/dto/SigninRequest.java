package com.Task.CustomerFeedBackSystem.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SigninRequest {
    @NotBlank(message = "Username is a required field")
    private String username;
    @NotBlank(message = "Password is a required field")
    private String password;
}
