package com.sparta.outsourcing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Menu extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Long price;

    public Menu(Long menuId, String menuName, Long price) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
    }

    public Menu(Restaurant restaurant, String menuName, Long price) {
        this.restaurant = restaurant;
        this.menuName = menuName;
        this.price = price;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}

