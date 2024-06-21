package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.CartRequestDto;
import com.sparta.outsourcing.dto.CartResponseDto;
import com.sparta.outsourcing.dto.OrderRequestDto;
import com.sparta.outsourcing.dto.OrderResponseDto;
import com.sparta.outsourcing.entity.Cart;
import com.sparta.outsourcing.entity.Menu;
import com.sparta.outsourcing.entity.Order;
//import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.repository.CartRepository;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CartRepository cartRepository;


    public void addCart(CartRequestDto requestDto) {
        Menu menu = menuRepository.findById(requestDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("없는 메뉴입니다."));
        Cart checkCart = cartRepository.findByMenuAndUserId(menu, requestDto.getUserId()).orElse(null);

        if (checkCart != null) {
            checkCart.setMenuCount(checkCart.getMenuCount() + requestDto.getMenuCount());

            cartRepository.save(checkCart);
        } else {
            Cart cart = new Cart();
            cart.setMenu(menu);
            cart.setUserId(requestDto.getUserId());
            cart.setMenuCount(requestDto.getMenuCount());
            cartRepository.save(cart);
        }
    }

    public List<CartResponseDto> getCart(long userId){
        List<Cart> cart = cartRepository.findByUserId(userId);
        return cart.stream()
                .map(CartResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public void updateCart(CartRequestDto requestDto) {
        Menu menu = menuRepository.findById(requestDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("없는 메뉴입니다."));

        Cart checkCart = cartRepository.findByMenuAndUserId(menu, requestDto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("장바구니에 담겨있지 않습니다."));

        checkCart.setMenuCount(requestDto.getMenuCount());
        cartRepository.save(checkCart);
    }

    public void deleteCart(CartRequestDto requestDto) {
        Menu menu = menuRepository.findById(requestDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("없는 메뉴입니다."));

        Cart checkCart = cartRepository.findByMenuAndUserId(menu, requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 담겨있지 않습니다."));

        cartRepository.delete(checkCart);
    }



}
