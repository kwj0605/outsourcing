package com.sparta.outsourcing.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BlacklistedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    public BlacklistedToken() {}

    public BlacklistedToken(String token, LocalDateTime expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }
}
