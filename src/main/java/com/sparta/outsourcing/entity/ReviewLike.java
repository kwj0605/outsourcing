package com.sparta.outsourcing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ReviewLike extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(nullable = false)
    private boolean Liked = false;

    public ReviewLike(User user, Review review) {
        this.user = user;
        this.review = review;
        this.Liked = false;
    }

    public void update() {
        this.Liked = !this.Liked;
    }
}
