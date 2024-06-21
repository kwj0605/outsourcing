package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.Restaurant;
import lombok.Getter;

@Getter
public class MenuDto {
    private final Restaurant restaurant;
    private final String menuName;
    private final Long price;

    public MenuDto(Restaurant restaurant, String menuName, Long price) {
        this.restaurant = restaurant;
        this.menuName = menuName;
        this.price = price;
    }
}
