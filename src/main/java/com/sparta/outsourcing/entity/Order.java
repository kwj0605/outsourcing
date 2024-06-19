package com.sparta.outsourcing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long like = 0L;

    public Long updateLike(boolean islike){
        if(islike){this.like -= 1;}
        else{this.like += 1;}
        return this.like;
    }
}
