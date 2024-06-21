package com.sparta.outsourcing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.outsourcing.dto.CartResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Entity
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
    private Long userId;

    private int menuCount;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMenuCount(int menuCount) {
        this.menuCount = menuCount;
    }

    public Cart(Menu menu, Long userId, int menuCount) {
        this.menu = menu;
        this.userId = userId;
        this.menuCount = menuCount;
    }


}
