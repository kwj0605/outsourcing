package com.sparta.outsourcing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantDto {

    private String restaurantName;
    private String restaurantInfo;
    private String phoneNumber;
    private Long likes;

    public RestaurantDto(String restaurantName, String restaurantInfo, String phoneNumber, Long likes) {
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
        this.phoneNumber = phoneNumber;
        this.likes = likes;
    }
}
