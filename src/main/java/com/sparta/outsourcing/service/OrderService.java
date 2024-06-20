package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.UpdateContentDto;
import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order updateOrder(Long orderId, UpdateContentDto updateOrderDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
        order.update(updateOrderDto.getContent());
        orderRepository.save(order);
        return order;
    }
}
