package com.sparta.outsourcing.service;

import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.entity.Review;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.enums.UserStatusEnum;
import com.sparta.outsourcing.repository.OrderRepository;
import com.sparta.outsourcing.repository.ReviewRepository;
import com.sparta.outsourcing.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BackOfficeService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public User updateUserStatus(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        if (user.getStatus().equals(UserStatusEnum.ACTIVE)) {user.setStatus(UserStatusEnum.DENIED);}
        else {user.setStatus(UserStatusEnum.ACTIVE);}
        return userRepository.save(user);
    }

    public User updateUserAuthority(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        if (user.getRole().equals(UserRoleEnum.USER)) {user.setRole(UserRoleEnum.ADMIN);}
        else {user.setRole(UserRoleEnum.USER);}
        return userRepository.save(user);
    }
}
