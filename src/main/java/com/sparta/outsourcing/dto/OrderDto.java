package com.sparta.outsourcing.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long restaurantId;
    private String payType;
    private List<OrderMenuDto> orderMenus;
}
