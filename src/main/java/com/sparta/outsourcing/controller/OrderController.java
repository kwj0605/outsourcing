package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.OrderDto;
import com.sparta.outsourcing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{restaurantId}/order")
    public ResponseEntity<String> placeOrder(@PathVariable Long restaurantId, @RequestBody OrderDto orderDto) {
        return orderService.placeOrder(restaurantId, orderDto);
    }
}
