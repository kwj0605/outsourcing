package com.sparta.outsourcing.service;

import com.sparta.outsourcing.repository.BlacklistedTokenRepository;

public class JwtBlacklistService {
    private BlacklistedTokenRepository blacklistedTokenRepository;

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }
}
