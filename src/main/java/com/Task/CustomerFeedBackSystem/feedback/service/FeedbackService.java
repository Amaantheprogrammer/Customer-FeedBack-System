package com.Task.CustomerFeedBackSystem.feedback.service;

import com.Task.CustomerFeedBackSystem.exception.ResourceNotFoundException;
import com.Task.CustomerFeedBackSystem.feedback.dto.CreateFeedbackRequest;
import com.Task.CustomerFeedBackSystem.feedback.dto.FeedbackResponse;
import com.Task.CustomerFeedBackSystem.feedback.dto.UpdateFeedbackRequest;
import com.Task.CustomerFeedBackSystem.feedback.entity.Feedback;
import com.Task.CustomerFeedBackSystem.feedback.repository.FeedbackRepository;
import com.Task.CustomerFeedBackSystem.user.entity.Role;
import com.Task.CustomerFeedBackSystem.user.entity.User;
import com.Task.CustomerFeedBackSystem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public FeedbackResponse getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID " + id));
        return modelMapper.map(feedback, FeedbackResponse.class);
    }

    @Transactional(readOnly = true)
    public Page<FeedbackResponse> getAllFeedbacks(Pageable pageable) {
        Page<Feedback> feedbacks = feedbackRepository.findAllFeedbacks(pageable);
        return feedbacks.map(feedback -> modelMapper.map(feedback, FeedbackResponse.class));
    }

    @Transactional(readOnly = true)
    public Page<FeedbackResponse> getAllFeedbacksByDateDesc(Pageable pageable) {
        Page<Feedback> feedbacks = feedbackRepository.findAllFeedbacksByDateDesc(pageable);
        return feedbacks.map(feedback -> modelMapper.map(feedback, FeedbackResponse.class));
    }

    @Transactional(readOnly = true)
    public List<FeedbackResponse> getAllFeedbacksByDate(LocalDate date) {
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByDate(date);
        return feedbacks.stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public FeedbackResponse createFeedback(CreateFeedbackRequest createFeedbackRequest) {
        Feedback feedback = Feedback.builder()
                .feedback(createFeedbackRequest.getFeedback())
                .date(LocalDate.now())
                .build();
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return modelMapper.map(savedFeedback, FeedbackResponse.class);
    }

    @Transactional
    public FeedbackResponse updateFeedback(Long id, UpdateFeedbackRequest updateFeedbackRequest) {
        User user = getCurrentUser();
        if (user.getRole() != Role.ROLE_ADMIN) {
            throw new AuthorizationDeniedException("Ordinary users cannot update feedback");
        }
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID " + id));
        feedback.setFeedback(updateFeedbackRequest.getFeedback());
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return modelMapper.map(savedFeedback, FeedbackResponse.class);
    }

    @Transactional
    public void deleteFeedback(Long id) {
        User user = getCurrentUser();
        if (user.getRole() != Role.ROLE_ADMIN) {
            throw new AuthorizationDeniedException("Ordinary users cannot delete feedback");
        }
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID " + id));
        if (!feedbackRepository.existsById(id)) {
            throw new ResourceNotFoundException("Feedback not found with ID " + id);
        }
        feedbackRepository.delete(feedback);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
}
