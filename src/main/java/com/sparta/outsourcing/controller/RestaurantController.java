package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.MenuDto;
import com.sparta.outsourcing.dto.RestaurantDto;
import com.sparta.outsourcing.dto.UserDto;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.security.UserDetailsImpl;
import com.sparta.outsourcing.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<String> addRestaurant(@RequestBody RestaurantDto restaurantDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return restaurantService.addRestaurant(restaurantDto, userDetails.getUser());
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@RequestBody Long restaurantId) {
        return restaurantService.deleteRestaurant(restaurantId);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurant(restaurantId);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        return restaurantService.getAllRestaurants(page, size);
    }


    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuDto>> getMenuList(@PathVariable Long restaurantId) {
        return restaurantService.getMenuList(restaurantId);
    }

    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<String> addMenu(@PathVariable Long restaurantId, @RequestBody MenuDto menuDto) {
        return restaurantService.addMenuToRestaurant(restaurantId, menuDto);
    }
}
