package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
