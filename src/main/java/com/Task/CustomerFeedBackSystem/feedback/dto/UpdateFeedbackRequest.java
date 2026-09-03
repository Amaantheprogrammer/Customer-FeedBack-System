package com.Task.CustomerFeedBackSystem.feedback.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateFeedbackRequest {
    @NotBlank(message = "Feedback is a required field")
    private String feedback;
}
