package com.sparta.outsourcing.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long retaurantId;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String phoneNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();
}
