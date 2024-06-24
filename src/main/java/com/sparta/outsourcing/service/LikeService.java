package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.LikeResponseDto;
import com.sparta.outsourcing.entity.*;
import com.sparta.outsourcing.exception.AlreadySignupException;
import com.sparta.outsourcing.exception.LikeSelfException;
import com.sparta.outsourcing.repository.RestaurantLikeRepository;
import com.sparta.outsourcing.repository.RestaurantRepository;
import com.sparta.outsourcing.repository.ReviewLikeRepository;
import com.sparta.outsourcing.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final RestaurantLikeRepository restaurantLikeRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final MessageSource messageSource;

    public LikeResponseDto updateRestaurantLike(Long contentId, User user) {

        Restaurant restaurant = restaurantRepository.findById(contentId).orElseThrow(() -> new RuntimeException("Restaurant not found"));

        if (user.getUsername().equals(restaurant.getUser().getUsername())) {
            throw new LikeSelfException(messageSource.getMessage(
                    "like.self", null, "본인이 작성한 컨텐츠에는 좋아요를 등록할 수 없습니다.", Locale.getDefault()
            ));
        }

        RestaurantLike restaurantLike = restaurantLikeRepository.findByUserAndRestaurant(user, restaurant)
                .orElseGet(() -> new RestaurantLike(user, restaurant));

        restaurantLike.update();
        restaurantLikeRepository.save(restaurantLike);

        return calculateRestaurantlike(restaurantLike, restaurant);
    }

    public LikeResponseDto updateReviewLike(Long contentId, User user) {
        Review review = reviewRepository.findById(contentId).orElseThrow(() -> new RuntimeException("Review not found"));

        if (user.getUsername().equals(review.getUser().getUsername())) {
            throw new LikeSelfException(messageSource.getMessage(
                    "like.self", null, "본인이 작성한 컨텐츠에는 좋아요를 등록할 수 없습니다.", Locale.getDefault()
            ));
        }

        ReviewLike reviewLike = reviewLikeRepository.findByUserAndReview(user, review)
                .orElseGet(() -> new ReviewLike(user, review));

        reviewLike.update();
        reviewLikeRepository.save(reviewLike);

        return calculateReviewlike(reviewLike, review);
    }

    private LikeResponseDto calculateRestaurantlike(RestaurantLike restaurantLike, Restaurant restaurant) {
        Long cnt =  restaurant.updateLike(restaurantLike.isLiked());
        return new LikeResponseDto(restaurantLike.isLiked(), cnt);
    }

    public LikeResponseDto calculateReviewlike(ReviewLike reviewLike, Review review) {
        Long cnt =  review.updateLike(reviewLike.isLiked());
        return new LikeResponseDto(reviewLike.isLiked(), cnt);
    }
}
