package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.OrderDto;
import com.sparta.outsourcing.dto.OrderMenuDto;
import com.sparta.outsourcing.dto.UpdateContentDto;
import com.sparta.outsourcing.entity.*;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.repository.OrderRepository;
import com.sparta.outsourcing.repository.RestaurantRepository;
import com.sparta.outsourcing.repository.UserRepository;
import com.sparta.outsourcing.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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

    public ResponseEntity<String> placeOrder(OrderDto orderDto) {
        logger.info("Placing order for restaurantId: {}", orderDto.getRestaurantId());
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(orderDto.getRestaurantId());
        User user = getUser();

        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            logger.info("Restaurant found: {}", restaurant.getRestaurantName());

            Order order = new Order(user, restaurant, calculateTotalPrice(orderDto), orderDto.getPayType());

            List<OrderMenu> orderMenus = new ArrayList<>();
            for (OrderMenuDto orderMenuDto : orderDto.getOrderMenus()) {
                Optional<Menu> optionalMenu = menuRepository.findById(orderMenuDto.getMenuId());
                if (optionalMenu.isPresent()) {
                    Menu menu = optionalMenu.get();
                    OrderMenu orderMenu = new OrderMenu(user, menu, order, orderMenuDto.getQuantity());
                    orderMenus.add(orderMenu);
                } else {
                    logger.error("Menu not found: {}", orderMenuDto.getMenuId());
                    return ResponseEntity.badRequest().body(orderMenuDto.getMenuId() + "번의 메뉴가 없습니다.");
                }
            }

            order.setOrderMenus(orderMenus);
            orderRepository.save(order);

            logger.info("Order placed successfully");
            return ResponseEntity.ok("주문이 성공적으로 완료되었습니다.");
        } else {
            logger.error("Restaurant not found: {}", orderDto.getRestaurantId());
            return ResponseEntity.notFound().build();
        }
    }

    private static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증되지 않은 사용자입니다.");
        }

        // Principal이 UserDetailsImpl 타입인지 확인
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl)) {
            throw new IllegalStateException("사용자 정보를 가져올 수 없습니다.");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        User currentUser = userDetails.getUser();
        return currentUser;
    }
}
