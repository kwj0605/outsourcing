package com.sparta.outsourcing.dto;

import lombok.Getter;

@Getter
public class ReviewRequest {

    private Long orderId;
    private Long userId;
    private String field;
    private Long like;

}
