package com.sparta.outsourcing.dto;

import lombok.Getter;

@Getter
public class RestaurantDto {

    private String restaurantName;
    private String restaurantInfo;
    private String phoneNumber;

    public RestaurantDto(String restaurantName, String restaurantInfo, String phoneNumber) {
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
        this.phoneNumber = phoneNumber;
    }
}
