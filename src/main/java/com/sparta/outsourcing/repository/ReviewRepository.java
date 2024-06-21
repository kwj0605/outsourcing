package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}
