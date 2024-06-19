package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.LikeResponseDto;
import com.sparta.outsourcing.entity.ContentType;
import com.sparta.outsourcing.entity.Like;
import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.entity.Review;
import com.sparta.outsourcing.repository.LikeRepository;
import com.sparta.outsourcing.repository.OrderRepository;
import com.sparta.outsourcing.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public LikeService(LikeRepository likeRepository, OrderRepository orderRepository, ReviewRepository reviewRepository) {
        this.likeRepository = likeRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
    }
    public LikeResponseDto updateLike(String contentTypeStr, Long contentId) {
        ContentType contentType = ContentType.valueOf(contentTypeStr);
        Like like = likeRepository.findByContentTypeAndContentId(contentType, contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 좋아요 정보가 없습니다."));
        Long likecnt = updateLikes(like);
        like.update();
        likeRepository.save(like);
        return new LikeResponseDto(likecnt);
    }

    private Long updateLikes(Like like) {

        Long likecnt;

        if (like.getContentType().equals(ContentType.Order)) {
            Order order = orderRepository.findById(like.getContentId()).orElseThrow();
            likecnt =  order.updateLike(like.isLiked());
        } else {
            Review review = reviewRepository.findById(like.getContentId()).orElseThrow();
            likecnt =review.updateLike(like.isLiked());
        }
        return likecnt;
    }
}
