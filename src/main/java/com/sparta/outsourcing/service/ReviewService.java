package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.ReviewRequest;
import com.sparta.outsourcing.dto.ReviewResponse;
import com.sparta.outsourcing.entity.Review;
import com.sparta.outsourcing.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Autowired
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    /**
     * 리뷰 생성
     */
    public ReviewResponse createReview(ReviewRequest request) {

        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 주문입니다.")
        );

        return new ReviewResponse(review);

    }

    /**
     * 리뷰 조회
     */

    public Review findReview(Long reviewId){
        Reivew review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("리뷰가 존재하지 않습니다.")
        );
        return review;
    }

    /**
     * 리뷰 수정
     */

    public ReviewResponse updateReview(Long reviewId, ReviewRequest request, User user){

        Review review = findReview(reviewId);

        if(!review.getUser().equals(user))
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");

        review.update(request);

        return new ReviewResponse(review);

    }


    /**
     * 리뷰 생성
     */
    public Review deleteReview(Long reviewId){

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("리뷰가 존재하지 않습니다.")
        );
        review.delete();

        return review;

    }

}
