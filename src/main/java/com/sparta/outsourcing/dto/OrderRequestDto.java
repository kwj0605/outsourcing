package com.sparta.outsourcing.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderRequestDto {

    private Long orderId;

    private Long userId;

    private int totalPrice;

    private String payType;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String orderStatus;

    private int likes;


}
