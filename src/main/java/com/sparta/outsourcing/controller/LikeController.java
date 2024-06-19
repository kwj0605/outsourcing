package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PutMapping("/{contentType}/{contentId}")
    public ResponseEntity<String> updateLike(@PathVariable("contentType") String contentType, @PathVariable Long contentId){
        likeService.updateLike(contentType, contentId);
        return ResponseEntity.ok("좋아요가 수정되었습니다.");
    }

}
