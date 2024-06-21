package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
