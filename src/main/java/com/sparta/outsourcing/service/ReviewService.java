package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.UpdateContentDto;
import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.entity.Review;
import com.sparta.outsourcing.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review updateReview(Long orderId, UpdateContentDto updateOrderDto) {
        Review review = reviewRepository.findById(orderId).orElseThrow(() ->new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
        review.update(updateOrderDto.getContent());
        reviewRepository.save(review);
        return review;
    }


}