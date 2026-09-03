package com.Task.CustomerFeedBackSystem.feedback.repository;

import com.Task.CustomerFeedBackSystem.feedback.entity.Feedback;
import org.springframework.data.domain.Page;

import java.time.*;
import java.util.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT f FROM Feedback f ORDER BY f.date DESC")
    Page<Feedback> findAllFeedbacksByDateDesc(Pageable pageable);

    @Query("SELECT f FROM Feedback f WHERE f.date = :date")
    List<Feedback> findFeedbacksByDate(@Param("date") LocalDate date);
}
