package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.ReviewDto;
import com.sparta.outsourcing.dto.UpdateContentDto;
import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.entity.Review;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.repository.OrderRepository;
import com.sparta.outsourcing.repository.ReviewRepository;
import com.sparta.outsourcing.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public ResponseEntity<String> addReview(ReviewDto reviewDto) {
        User user = getUser();

        Optional<Order> optionalOrder = orderRepository.findById(reviewDto.getOrderId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            Review review = new Review(user, order, reviewDto.getContent());

            reviewRepository.save(review);
            return ResponseEntity.ok("리뷰가 성공적으로 작성되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> updateReview(ReviewDto reviewDto) {
        User user = getUser();


        Optional<Review> optionalReview = reviewRepository.findById(reviewDto.getOrderId());
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();

            if (review.getUser().getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
                review.update(reviewDto.getContent());
                reviewRepository.save(review);
                return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
            } else {
                return ResponseEntity.status(403).body("수정할 권한이 없습니다.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> deleteReview(Long reviewId) {
        User user = getUser();

        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            if (review.getUser().getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
                reviewRepository.delete(review);
                return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(403).body("삭제할 권한이 없습니다.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증되지 않은 사용자입니다.");
        }

        // Principal이 UserDetailsImpl 타입인지 확인
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl)) {
            throw new IllegalStateException("사용자 정보를 가져올 수 없습니다.");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        User currentUser = userDetails.getUser();
        return currentUser;
    }

}