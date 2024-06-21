package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.repository.RestaurantRepository;
import com.sparta.outsourcing.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/store")
@RestController
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("")
    public ResponseEntity<String> createRestaurant() {
        return null;
    }
    @GetMapping("")
    public ResponseEntity<String> getRestaurant() {
        return null;
    }
    @PostMapping("")
    public ResponseEntity<String> updateRestaurant() {
        return null;
    }
    @PostMapping("")
    public ResponseEntity<String> deleteRestaurant() {
        return null;
    }
}
