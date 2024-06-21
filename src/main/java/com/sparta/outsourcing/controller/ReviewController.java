package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.ReviewRequest;
import com.sparta.outsourcing.dto.ReviewResponse;
import com.sparta.outsourcing.dto.ReviewUpdateResquest;
import com.sparta.outsourcing.entity.Review;
import com.sparta.outsourcing.repository.ReviewRepository;
import com.sparta.outsourcing.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Getter
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;


    /**
     * 리뷰 생성
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ReviewResponse> CreateReview(@RequestBody ReviewRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        ReviewResponse response =reviewService.createReview(request, userDetails.getUser());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * 리뷰 수정
     */
    @Transactional
    @PutMapping("{reviewId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long reviewId,
                                               @RequestBody ReviewUpdateResquest reviewUpdateResquest,
                                               @AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok().body( reviewService.updateReview(reviewId ,reviewUpdateResquest, userDetails));

    }

    /**
     * 리뷰 조회
     */

    @GetMapping("{reviewId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Review> findReview(@PathVariable Long reviewId){

        return ResponseEntity.ok().body( reviewService.findReview(reviewId));

    }

    /**
     * 리뷰 삭제
     */    
    @DeleteMapping("{reviewId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId,
                                             @AuthenticationPrincipal UserDetails userDetails){
        reviewService.deleteReview(reviewId, userDetails);

        return ResponseEntity.noContent().build();

    }

}
