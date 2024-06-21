package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.OrderRequestDto;
import com.sparta.outsourcing.dto.OrderResponseDto;
import com.sparta.outsourcing.entity.Menu;
import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.repository.OrderRepository;
import com.sparta.outsourcing.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class OrderService {
    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, MenuRepository menuRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }

    // 주문 등록
    public OrderResponseDto createOrder(long userId, List<OrderRequestDto> menuList) {
//        User user = findUser(userId);

        List<String> menus = getMenus(menuList);
        int totalPrice = getTotalPrice(menuList);

        Order order = new Order();
        order.setUserId(userId);
//        order.setOrderStatus("orderStatus");
        order.setMenuList(menus);
        order.setTotalPrice(totalPrice);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return OrderResponseDto.toDto(order);
    }

    public List<OrderResponseDto> getOrders() {
        return orderRepository.findAll().stream().map(OrderResponseDto::toDto).toList();
    }

    //menuId로 메뉴 찾기
    private Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() ->
                new IllegalArgumentException("해당 메뉴을 찾을 수 없습니다."));
    }

    //주문 메뉴 목록
    private List<String> getMenus(List<OrderRequestDto> menuList) {
        List<String> menus = new ArrayList<>();
        for (OrderRequestDto requestDto : menuList) {
            Menu menu = findMenuById(requestDto.getMenuId());
            String count = Integer.toString(requestDto.getMenuCount());
            menus.add(menu.getMenuName() + " 수량: " + count );
        }
        return menus;
    }

    // 주문 총 가격
    private int getTotalPrice(List<OrderRequestDto> menuList) {
        int totalPrice = 0;
        for (OrderRequestDto requestDto : menuList) {
            Menu menu = findMenuById(requestDto.getMenuId());
            int menuPrice = menu.getPrice();
            int count = requestDto.getMenuCount();
            totalPrice += menuPrice*count;
        }
        return totalPrice;
    }


//    protected User findUser(long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
//    }
}
