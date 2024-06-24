package com.sparta.outsourcing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDto {

    private Long orderId;
    private String content;

    public ReviewDto(Long orderId, String content) {
        this.orderId = orderId;
        this.content = content;
    }
}
