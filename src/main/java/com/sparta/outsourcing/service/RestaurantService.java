package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.MenuDto;
import com.sparta.outsourcing.dto.RestaurantDto;
import com.sparta.outsourcing.entity.Menu;
import com.sparta.outsourcing.entity.Restaurant;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.repository.RestaurantRepository;
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
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public ResponseEntity<String> addRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant(restaurantDto.getRestaurantName(), restaurantDto.getRestaurantInfo(), restaurantDto.getPhoneNumber());
        restaurantRepository.save(restaurant);

        return ResponseEntity.ok("식당이 등록되었습니다.");
    }

    public ResponseEntity<String> deleteRestaurant(Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurantRepository.delete(restaurant);
            return ResponseEntity.ok("식당 정보가 삭제되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> addMenuToRestaurant(Long restaurantId, MenuDto menuDto) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();

            Menu menu = new Menu(menuDto.getRestaurant(), menuDto.getMenuName(), menuDto.getPrice());

            if (restaurant.getMenuList() == null) {
                restaurant.setMenuList(new ArrayList<>());
            }
            restaurant.getMenuList().add(menu);

            menuRepository.save(menu);
            restaurantRepository.save(restaurant);

            return ResponseEntity.ok("메뉴가 성공적으로 등록되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<RestaurantDto> getRestaurant(Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            RestaurantDto restaurantDto = new RestaurantDto(restaurant.getRestaurantName(), restaurant.getRestaurantInfo(), restaurant.getPhoneNumber());
            return ResponseEntity.ok(restaurantDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<MenuDto>> getMenuList(Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            List<Menu> menuList = restaurant.getMenuList();
            List<MenuDto> menuDtoList = new ArrayList<>();

            for (Menu menu : menuList) {
                MenuDto menuDto = new MenuDto(menu.getRestaurant(), menu.getMenuName(), menu.getPrice());
                menuDtoList.add(menuDto);
            }

            return ResponseEntity.ok(menuDtoList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
