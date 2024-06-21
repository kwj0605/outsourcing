package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.CartRequestDto;
import com.sparta.outsourcing.dto.CartResponseDto;
import com.sparta.outsourcing.dto.OrderRequestDto;
import com.sparta.outsourcing.dto.OrderResponseDto;
import com.sparta.outsourcing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/cart")
    public ResponseEntity<String> addCart(@RequestBody CartRequestDto requestDto) {
        orderService.addCart(requestDto);
        return new ResponseEntity<>("장바구니 등록", HttpStatus.OK);
    }

    @GetMapping("/cart/{userId}")
    public List<CartResponseDto> getCart(@PathVariable Long userId){
        orderService.getCart(userId);
        return orderService.getCart(userId);
    }

    @PutMapping("/cart")
    public ResponseEntity<String> updateCart(@RequestBody CartRequestDto requestDto) {
        orderService.updateCart(requestDto);
        return new ResponseEntity<>("장바구니 수정 성공", HttpStatus.OK);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> deleteCart(@RequestBody CartRequestDto requestDto) {
        orderService.deleteCart(requestDto);
        return new ResponseEntity<>("장바구니 메뉴 삭제", HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto requestDto) {
//        orderService.createOrder(requestDto);
//
//        return ResponseEntity.ok().body(orderService.createOrder(requestDto));
//    }

    // 주문 목록 조회
//    @GetMapping
//    public ResponseEntity<String> getAllOrders(){
//
//    }

//    @PutMapping("/{order_id}")
//    public ResponseEntity<String> updateOrder(@PathVariable(name = "order_id") long id) {
//
//    }
//
//    @DeleteMapping("/{id]")
//    public void deleteOrder() {
//
//    }
}
