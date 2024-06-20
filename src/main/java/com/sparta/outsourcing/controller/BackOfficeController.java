package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.CustomUserDetails;
import com.sparta.outsourcing.dto.ProfileDto;
import com.sparta.outsourcing.dto.UpdateContentDto;
import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.entity.Review;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.UserStatusEnum;
import com.sparta.outsourcing.repository.UserRepository;
import com.sparta.outsourcing.service.BackOfficeService;
import com.sparta.outsourcing.service.OrderService;
import com.sparta.outsourcing.service.ReviewService;
import com.sparta.outsourcing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class BackOfficeController {
    private final UserService userService;
    private final OrderService orderService;
    private final ReviewService reviewService;
    private final UserRepository userRepository;
    private final BackOfficeService backOfficeService;

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = backOfficeService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/order")
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = backOfficeService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/review")
    public ResponseEntity<?> getAllReviews() {
        List<Review> reviews = backOfficeService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/user/userprofile/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        User user = userRepository.findById(userId).orElseThrow();
        ResponseEntity<String> result = userService.updateProfile(userId, Objects.requireNonNull(user), profileDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/user/userstatus/{userId}")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long userId) {
        User user = backOfficeService.updateUserStatus(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/userauth/{userId}")
    public ResponseEntity<?> updateUserAuthority(@PathVariable Long userId) {
        User user = backOfficeService.updateUserAuthority(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        User user = backOfficeService.updateUserStatus(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderId, @RequestBody UpdateContentDto updateContentDto) {
        Order updateOrder = orderService.updateOrder(orderId, updateContentDto);
        return ResponseEntity.ok(updateOrder);
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody UpdateContentDto updateContentDto) {
        Review updateReview = reviewService.updateReview(reviewId, updateContentDto);
        return ResponseEntity.ok(updateReview);
    }
}
