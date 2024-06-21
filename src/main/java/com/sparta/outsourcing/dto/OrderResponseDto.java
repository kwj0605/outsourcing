package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.Cart;
import com.sparta.outsourcing.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponseDto {
    //    private User user;
    private Long userId;

    private int totalPrice;

    private String payType;

    private LocalDateTime createdAt;

//    private LocalDateTime modifiedAt;

    private String orderStatus;

    private int likes;

    private List<Cart> menuList;

    public OrderResponseDto(Long userId, List<Cart> menuList, String payType, int totalPrice, LocalDateTime createdAt) {
        this.userId = userId;
        this.menuList = menuList;
        this.payType = payType;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public static OrderResponseDto toDto(Order order) {
        return new OrderResponseDto(
                order.getUserId(),
                order.getMenuList(),
                order.getPayType(),
                order.getTotalPrice(),
                order.getCreatedAt()
        );
    }
}
