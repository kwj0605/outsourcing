package com.sparta.outsourcing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequestDto {
    private long menuId;
    private long userId;
    private int menuCount;
}
