package com.sparta.outsourcing.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

//    @ManyToOne
//    @JoinColumn(name="user_id", nullable = false)
//    private User user;
    private Long userId;

    private int totalPrice;

    private String payType;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String orderStatus;

    private int likes = 0;

    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<Cart> menuList = new ArrayList<>();

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setMenuList(List<Cart> menuList) {
        this.menuList = menuList;
    }
}
