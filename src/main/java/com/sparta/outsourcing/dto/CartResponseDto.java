package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.Cart;
import com.sparta.outsourcing.entity.Menu;
import lombok.Getter;

@Getter
public class CartResponseDto {
    private String menuName;
    private int menuCount;

    public CartResponseDto(Menu menu, int menuCount) {
        this.menuName = menu.getMenuName();
        this.menuCount = menuCount;
    }

    public static CartResponseDto toDto(Cart cart) {
        return new CartResponseDto(
                cart.getMenu(),
                cart.getMenuCount()
                );
    }
}
