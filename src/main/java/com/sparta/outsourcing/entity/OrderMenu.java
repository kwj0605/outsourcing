package com.sparta.outsourcing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public OrderMenu(Menu menu, Order order, int quantity) {
        this.menu = menu;
        this.order = order;
        this.quantity = quantity;
    }
}




