package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.ReviewDto;
import com.sparta.outsourcing.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.addReview(reviewDto);
    }

    @PutMapping
    public ResponseEntity<String> updateReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.updateReview(reviewDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}
