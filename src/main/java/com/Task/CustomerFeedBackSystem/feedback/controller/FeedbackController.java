package com.Task.CustomerFeedBackSystem.feedback.controller;

import com.Task.CustomerFeedBackSystem.feedback.dto.CreateFeedbackRequest;
import com.Task.CustomerFeedBackSystem.feedback.dto.FeedbackResponse;
import com.Task.CustomerFeedBackSystem.feedback.dto.UpdateFeedbackRequest;
import com.Task.CustomerFeedBackSystem.feedback.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import java.time.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @GetMapping
    public ResponseEntity<Page<FeedbackResponse>> getAllFeedbacks(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks(pageable));
    }

    @GetMapping("/date")
    public ResponseEntity<Page<FeedbackResponse>> getAllFeedbacksByDateDesc(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(feedbackService.getAllFeedbacksByDateDesc(pageable));
    }

    @GetMapping("/date-desc")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacksByDate(LocalDate date) {
        return ResponseEntity.ok(feedbackService.getAllFeedbacksByDate(date));
    }

    @PostMapping
    public ResponseEntity<FeedbackResponse> createFeedback(@RequestBody @Valid CreateFeedbackRequest createFeedbackRequest) {
        return ResponseEntity.ok(feedbackService.createFeedback(createFeedbackRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponse> updateFeedback(@PathVariable Long id,@RequestBody @Valid UpdateFeedbackRequest updateFeedbackRequest) {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, updateFeedbackRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

}
