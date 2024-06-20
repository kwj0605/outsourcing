package com.sparta.outsourcing.entity;

import com.sparta.outsourcing.enums.ContentTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Like extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentTypeEnum contentType;

    @Column(nullable = false)
    private Long contentId;

    @Column(nullable = false)
    private boolean Liked = false;

    public Like(User user, ContentTypeEnum contentType, Long contentId, boolean liked) {
        this.user = user;
        this.contentType = contentType;
        this.contentId = contentId;
        this.Liked = liked;
    }

    public void update() {
        this.Liked = !this.Liked;
    }
}
