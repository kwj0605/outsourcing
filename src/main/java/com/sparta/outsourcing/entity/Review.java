package com.sparta.outsourcing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
@Getter
@Entity
@NoArgsConstructor
public class Review {
=======
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Review extends Timestamped {
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
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    public Review(User user, Order order, String content) {
        this.user = user;
        this.order = order;
        this.content = content;
    }

    public Long updateLike(boolean islike){
        if(islike){this.likes += 1;}
        else{this.likes -= 1;}
        return this.likes;
    }

    public void update(String newContent){
        this.content = newContent;
>>>>>>> 9e993afb4ad9379a8a5efc1420377b971f931cce
    }
}
