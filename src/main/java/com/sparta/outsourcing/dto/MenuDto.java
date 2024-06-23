package com.sparta.outsourcing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuDto {

    private String menuName;
    private Long price;

    public MenuDto(String menuName, Long price) {
        this.menuName = menuName;
        this.price = price;
    }
}
