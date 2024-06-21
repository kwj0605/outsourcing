package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.LikeResponseDto;
import com.sparta.outsourcing.enums.ContentTypeEnum;
import com.sparta.outsourcing.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    @Autowired
    private final LikeService likeService;

    @PutMapping("/{contentType}/{contentId}")
    public ResponseEntity<String> updateLike(@PathVariable ContentTypeEnum contentType, @PathVariable Long contentId){
        LikeResponseDto likeResponseDto = likeService.updateLike(contentType, contentId);
        if (likeResponseDto.isLiked()) {
            return ResponseEntity.ok("좋아요를 눌렀습니다! 다른 콘텐츠도 확인해보세요 : " + likeResponseDto.getCnt());
        } else {
            return ResponseEntity.ok("좋아요를 취소했습니다. 다시 좋아요를 누를 수 있습니다 : " + likeResponseDto.getCnt());
        }
    }
}
