package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.Order;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponseDto {
    private long userId;
    private int totalPrice;
    private List<String> menuList;

    public OrderResponseDto(long userId, List<String> menuList, int totalPrice) {
        this.userId = userId;
        this.menuList = menuList;
        this.totalPrice = totalPrice;
    }

    public static OrderResponseDto toDto(Order order) {
        return new OrderResponseDto(
                order.getUserId(),
                order.getMenuList(),
                order.getTotalPrice()
        );
    }
}
