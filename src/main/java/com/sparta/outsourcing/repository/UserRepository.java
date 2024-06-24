package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.AuthEnum;
import com.sparta.outsourcing.enums.UserStatusEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    Optional<User> findUserByUsernameAndStatus(String username, UserStatusEnum status);
    boolean existsByUsername(String username);
    Optional<User> findByRefreshtoken(String refreshToken);
}
