package com.sparta.outsourcing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD

@Getter
@Entity
@NoArgsConstructor
public class Order {
=======
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped {
>>>>>>> 9e993afb4ad9379a8a5efc1420377b971f931cce
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @Column(nullable = false)
    private Long like = 0L;

    public Long updateLike(boolean islike){
        if(islike){this.like -= 1;}
        else{this.like += 1;}
        return this.like;
=======
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String payType;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    public Order(User user, Restaurant restaurant, int totalPrice, String payType) {
        this.user = user;
        this.restaurant = restaurant;
        this.totalPrice = totalPrice;
        this.payType = payType;
>>>>>>> 9e993afb4ad9379a8a5efc1420377b971f931cce
    }
}
