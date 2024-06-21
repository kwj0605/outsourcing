package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponse {
    private String nickname;
    private String restaurant;
    private String menu;
    private String field;
    private Long like;

}
