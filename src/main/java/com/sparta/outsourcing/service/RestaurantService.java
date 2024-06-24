package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.MenuDto;
import com.sparta.outsourcing.dto.RestaurantDto;
import com.sparta.outsourcing.entity.Menu;
import com.sparta.outsourcing.entity.Restaurant;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.exception.InvalidAccessException;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.repository.RestaurantRepository;
import com.sparta.outsourcing.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MessageSource messageSource;

    public ResponseEntity<String> addRestaurant(RestaurantDto restaurantDto, User user) {
        Restaurant restaurant = new Restaurant(user, restaurantDto.getRestaurantName(), restaurantDto.getRestaurantInfo(), restaurantDto.getPhoneNumber());
        restaurantRepository.save(restaurant);

        return ResponseEntity.ok("식당이 등록되었습니다.");
    }

    public ResponseEntity<String> deleteRestaurant(Long restaurantId) {
        User user = getUser();
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            if (restaurant.getUser().getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
                restaurantRepository.delete(restaurant);
                return ResponseEntity.ok("식당 정보가 삭제되었습니다.");
            } else {
                throw new InvalidAccessException(messageSource.getMessage(
                        "invalid.access", null, "적합하지 않은 접근입니다.", Locale.getDefault()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("식당 정보가 존재하지 않습니다.");
        }
    }

    public ResponseEntity<String> addMenuToRestaurant(Long restaurantId, MenuDto menuDto) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        User user = getUser();
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();

            if (restaurant.getUser().getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
                Menu menu = new Menu(restaurant, menuDto.getMenuName(), menuDto.getPrice());

                if (restaurant.getMenuList() == null) {
                    restaurant.setMenuList(new ArrayList<>());
                }
                restaurant.getMenuList().add(menu);

                menuRepository.save(menu);
                restaurantRepository.save(restaurant);

                return ResponseEntity.ok("메뉴가 성공적으로 등록되었습니다.");
            } else {
                throw new InvalidAccessException(messageSource.getMessage(
                        "invalid.access", null, "적합하지 않은 접근입니다.", Locale.getDefault()));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<RestaurantDto> getRestaurant(Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            RestaurantDto restaurantDto = new RestaurantDto(restaurant.getRestaurantName(), restaurant.getRestaurantInfo(),
                    restaurant.getPhoneNumber(), restaurant.getLikes());
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
                MenuDto menuDto = new MenuDto(menu.getMenuName(), menu.getPrice());
                menuDtoList.add(menuDto);
            }

            return ResponseEntity.ok(menuDtoList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Restaurant> restaurantPage = restaurantRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<RestaurantDto> restaurantDtoList = restaurantPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(restaurantDtoList);
    }

    private RestaurantDto convertToDto(Restaurant restaurant) {
        return new RestaurantDto(restaurant.getRestaurantName(), restaurant.getRestaurantInfo(),
                restaurant.getPhoneNumber(), restaurant.getLikes());
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
