package com.sparta.outsourcing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Order extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="restaurantId", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String payType;

    @Setter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @Column(nullable = false)
    private String orderStatus;

    public Order(User user, Restaurant restaurant, int totalPrice, String payType) {
        this.user = user;
        this.restaurant = restaurant;
        this.totalPrice = totalPrice;
        this.payType = payType;
    }
}
