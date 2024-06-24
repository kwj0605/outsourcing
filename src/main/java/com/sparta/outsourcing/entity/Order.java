package com.sparta.outsourcing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
//    private long userId;

    private int totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String orderStatus;

    @ElementCollection // 컬렉션 객체임을 알려줌
    @CollectionTable(name = "menu_order", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "menu_count")
    private List<String> menuList = new ArrayList<>();

}
