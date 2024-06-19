package com.sparta.outsourcing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType contentType;

    @Column(nullable = false)
    private Long contentId;

    @Column(nullable = false)
    private boolean Like = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    public Like(User user, ContentType contentType, Long contentId, boolean like) {
        this.user = user;
        this.contentType = contentType;
        this.contentId = contentId;
        this.Like = like;
    }

    public void update() {
        this.Like = !this.Like;
    }
}
