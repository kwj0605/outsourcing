package com.sparta.outsourcing.entity;

import com.sparta.outsourcing.dto.ReviewRequest;
import com.sparta.outsourcing.dto.ReviewUpdateResquest;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Entity
@Getter
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "nickname")
    private Nickname nickname;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "field", nullable = false)
    private String field;

    @Column(name = "like", nullable = false)
    private Long like;


    public Review(ReviewRequest request, User user) {
        this.user = request.getUser();
        this.order = request.getOrder();
        this.field = request.getField();
        this.like = request.getLike();
    }

    public void UpdateReview(ReviewUpdateResquest updateRequest, User user){
        this.field = updateRequest.getField();
    }

}
