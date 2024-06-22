package com.sparta.outsourcing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Review extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private Long likes = 0L;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewLike> reviewLikes;

    public Long updateLike(boolean islike){
        if(islike){this.likes += 1;}
        else{this.likes -= 1;}
        return this.likes;
    }

    public void update(String newContent){
        this.content = newContent;
    }
}
