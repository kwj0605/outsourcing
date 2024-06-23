package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.Restaurant;
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
