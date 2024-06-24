package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.OrderRequestDto;
import com.sparta.outsourcing.dto.OrderResponseDto;
import com.sparta.outsourcing.entity.Menu;
import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.repository.OrderRepository;
import com.sparta.outsourcing.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        List<Long> retaurant = checkRestaurant(menuList);
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

    // 모든 주문 조회
    public Page<OrderResponseDto> getOrders(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(OrderResponseDto::toDto);
    }

    // 특정 주문 조회
    public List<OrderResponseDto> getOrder(long orderId) {
        return orderRepository.findById(orderId).stream().map(OrderResponseDto::toDto).toList();
    }

    // 주문 수정
    public OrderResponseDto updateOrder(long orderId, List<OrderRequestDto> menuList) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        List<String> menus = getMenus(menuList);
        int totalPrice = getTotalPrice(menuList);

        order.setMenuList(menus);
        order.setTotalPrice(totalPrice);
        order.setModifiedAt(LocalDateTime.now());
        orderRepository.save(order);
        return OrderResponseDto.toDto(order);
    }

    // 주문 삭제
    public void deleteOrder(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        orderRepository.delete(order);
    }

    //menuId로 메뉴 찾기
    private Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() ->
                new IllegalArgumentException("해당 메뉴을 찾을 수 없습니다."));
    }

    // 다른 가게인지 체크
    private List<Long> checkRestaurant(List<OrderRequestDto> menuList) {
        List<Long> restaurants = new ArrayList<>();
        for (OrderRequestDto requestDto : menuList) {
            Menu menu = findMenuById(requestDto.getMenuId());
            restaurants.add(menu.getRestaurant().getRetaurantId());
        }
        if (restaurants.stream().distinct().count() != 1) {
            throw new IllegalArgumentException("같은 가게에서만 주문할 수 있습니다.");
        }
        return restaurants;
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
            totalPrice += menu.getPrice()*requestDto.getMenuCount();
        }
        return totalPrice;
    }



//    protected User findUser(long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
//    }
}
