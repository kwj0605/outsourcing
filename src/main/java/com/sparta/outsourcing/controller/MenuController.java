package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.MenuDto;
import com.sparta.outsourcing.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/menu")
@RestController
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<MenuDto>> getMenuList(@PathVariable Long restaurantId) {
        return menuService.getMenuList(restaurantId);
    }

    @PostMapping("/{restaurantId}")
    public ResponseEntity<String> addMenu(@PathVariable("restaurantId") Long restaurantId, @RequestBody MenuDto menuDto) {
        return menuService.addMenuToRestaurant(restaurantId, menuDto);
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<String> updateMenu(@PathVariable("menuId") Long menuId, @RequestBody MenuDto menuDto) {
        return menuService.updateMenu(menuId, menuDto);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable("menuId") Long menuId) {
        return menuService.deleteMenu(menuId);
    }
}
