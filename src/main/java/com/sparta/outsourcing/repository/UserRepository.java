package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.User;
import java.util.Optional;

import jakarta.persistence.Entity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
}
