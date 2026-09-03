package com.Task.CustomerFeedBackSystem.feedback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String feedback;

    @Column(nullable = false)
    private LocalDate date;
}
