package com.sparta.outsourcing.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.outsourcing.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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
    private UserStatusEnum status = UserStatusEnum.ACTIVE;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false) // 카멜케이스 사용, 외래키명 변경
    private User user;

    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Menu> menuList;

    @Column(nullable = false)
    private Long likes = 0L;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantLike> restaurantLikeList = new ArrayList<>();

    public Restaurant(User user, String restaurantName, String restaurantInfo, String phoneNumber) {
        this.user = user;
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
        this.phoneNumber = phoneNumber;
        this.likes = 0L;
    }

    public Long updateLike(boolean islike){
        if(islike){this.likes += 1;}
        else{this.likes -= 1;}
        return this.likes;
    }
}
