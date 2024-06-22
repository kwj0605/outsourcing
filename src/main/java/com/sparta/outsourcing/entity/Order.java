package com.sparta.outsourcing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders") // 'orders'로 테이블명 변경
public class Order extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false) // 카멜케이스 사용, 외래키명 변경
    private User user;

    @ManyToOne
    @JoinColumn(name="restaurant_id", nullable = false) // 카멜케이스 사용, 외래키명 변경
    private Restaurant restaurant;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String payType;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // orphanRemoval 추가
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @Column(nullable = false, name = "order_status") // 'order_status'로 컬럼명 변경
    private String orderStatus;

    public Order(User user, Restaurant restaurant, int totalPrice, String payType) {
        this.user = user;
        this.restaurant = restaurant;
        this.totalPrice = totalPrice;
        this.payType = payType;
    }
}
