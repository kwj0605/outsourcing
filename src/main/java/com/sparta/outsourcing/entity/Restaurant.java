package com.sparta.outsourcing.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.outsourcing.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String restaurantInfo;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private UserStatusEnum staus = UserStatusEnum.ACTIVE;

    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Menu> menuList;

    @Column(nullable = false)
    private Long like = 0L;

    public Restaurant(String restaurantName, String restaurantInfo, String phoneNumber) {
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
        this.phoneNumber = phoneNumber;
    }

    public Long updateLike(boolean islike){
        if(islike){this.like -= 1;}
        else{this.like += 1;}
        return this.like;
    }
}
