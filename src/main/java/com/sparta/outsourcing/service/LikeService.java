package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.LikeResponseDto;
import com.sparta.outsourcing.entity.Like;
import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.entity.Restaurant;
import com.sparta.outsourcing.entity.Review;
import com.sparta.outsourcing.enums.ContentTypeEnum;
import com.sparta.outsourcing.repository.LikeRepository;
import com.sparta.outsourcing.repository.OrderRepository;
import com.sparta.outsourcing.repository.RestaurantRepository;
import com.sparta.outsourcing.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    public LikeResponseDto updateLike(ContentTypeEnum contentType, Long contentId) {
        Like like = likeRepository.findByContentTypeAndContentId(contentType, contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 좋아요 정보가 없습니다."));
        LikeResponseDto likeResponseDto = calculateLike(like);
        like.update();
        likeRepository.save(like);
        return likeResponseDto;
    }

    private LikeResponseDto calculateLike(Like like) {

        Long cnt;

        if (like.getContentType().equals(ContentTypeEnum.RESTAURANT)) {
            Restaurant restaurant = restaurantRepository.findById(like.getContentId()).orElseThrow();
            cnt =  restaurant.updateLike(like.isLiked());
        } else {
            Review review = reviewRepository.findById(like.getContentId()).orElseThrow();
            cnt =review.updateLike(like.isLiked());
        }
        return new LikeResponseDto(!like.isLiked(), cnt);
    }
}
