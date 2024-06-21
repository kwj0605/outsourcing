package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.Cart;
import com.sparta.outsourcing.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMenuAndUserId(Menu menu, Long userId);

    List<Cart> findByUserId(Long userId);
}
