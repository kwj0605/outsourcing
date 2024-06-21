package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
