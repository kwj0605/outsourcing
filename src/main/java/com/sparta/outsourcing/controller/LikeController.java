package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.LikeResponseDto;
import com.sparta.outsourcing.enums.ContentTypeEnum;
import com.sparta.outsourcing.exception.LikeSelfException;
import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    /**
    *  게시물에 대한 좋아요 추가 및 취소 컨트롤러
     * @param contentType : {RESTAURANT | REVIEW}
     * @param contentId : RESTAURANT 혹은 REVIEW의 Id
     *
     *                  @return 200 ok
    **/
    @PutMapping("/{contentType}/{contentId}")
    public ResponseEntity<String> updateRestaurantLike(@PathVariable("contentType") ContentTypeEnum contentType, @PathVariable("contentId") Long contentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws LikeSelfException {

        LikeResponseDto likeResponseDto;

        if (contentType.equals(ContentTypeEnum.RESTAURANT)) {
            likeResponseDto = likeService.updateRestaurantLike(contentId, userDetails.getUser());
        } else {
            likeResponseDto = likeService.updateReviewLike(contentId, userDetails.getUser());
        }

        if (likeResponseDto.isLiked()) {
            return ResponseEntity.ok("좋아요를 눌렀습니다! 다른 콘텐츠도 확인해보세요 : " + likeResponseDto.getCnt());
        } else {
            return ResponseEntity.ok("좋아요를 취소했습니다. 다시 좋아요를 누를 수 있습니다 : " + likeResponseDto.getCnt());
        }
    }
}
