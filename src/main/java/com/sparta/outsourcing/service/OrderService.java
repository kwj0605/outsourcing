package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.OrderDto;
import com.sparta.outsourcing.dto.OrderMenuDto;
import com.sparta.outsourcing.dto.UpdateContentDto;
import com.sparta.outsourcing.entity.*;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.repository.OrderRepository;
import com.sparta.outsourcing.repository.RestaurantRepository;
import com.sparta.outsourcing.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private int calculateTotalPrice(OrderDto orderDto) {
        int totalPrice = 0;
        for (OrderMenuDto orderMenuDto : orderDto.getOrderMenus()) {
            Optional<Menu> optionalMenu = menuRepository.findById(orderMenuDto.getMenuId());
            if (optionalMenu.isPresent()) {
                Menu menu = optionalMenu.get();
                totalPrice += (int) (menu.getPrice() * orderMenuDto.getQuantity());
            }
        }
        return totalPrice;
    }

    public ResponseEntity<String> placeOrder(Long restaurantId, OrderDto orderDto) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Optional<User> optionalUser = userRepository.findById(orderDto.getUserId());

        if (optionalRestaurant.isPresent() && optionalUser.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            User user = optionalUser.get();

            Order order = new Order(user, restaurant, calculateTotalPrice(orderDto), orderDto.getPayType());

            List<OrderMenu> orderMenus = new ArrayList<>();
            for (OrderMenuDto orderMenuDto : orderDto.getOrderMenus()) {
                Optional<Menu> optionalMenu = menuRepository.findById(orderMenuDto.getMenuId());
                if (optionalMenu.isPresent()) {
                    Menu menu = optionalMenu.get();
                    OrderMenu orderMenu = new OrderMenu(menu, order, orderMenuDto.getQuantity());
                    orderMenus.add(orderMenu);
                } else {
                    return ResponseEntity.badRequest().body(orderMenuDto.getMenuId() + "번의 메뉴가 없습니다.");
                }
            }

            order.setOrderMenus(orderMenus);
            orderRepository.save(order);

            return ResponseEntity.ok("주문이 성공적으로 완료되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
