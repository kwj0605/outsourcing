package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token);
}
