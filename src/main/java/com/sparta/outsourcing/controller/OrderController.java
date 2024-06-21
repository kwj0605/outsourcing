package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.OrderRequestDto;
import com.sparta.outsourcing.dto.OrderResponseDto;
import com.sparta.outsourcing.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable long userId, @RequestBody List<OrderRequestDto> menuList) {
        OrderResponseDto responseDto = orderService.createOrder(userId, menuList);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping
    public List<OrderResponseDto> getOrders() {
        return orderService.getOrders();
    }
}
