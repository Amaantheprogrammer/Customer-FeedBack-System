package com.Task.CustomerFeedBackSystem.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackResponse {
    private Long id;
    private String feedback;
    private LocalDate date;
}
