package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.MenuDto;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/menu")
@RestController
public class MenuController {
    private final MenuService menuService;
    
    @PostMapping("/")
    public ResponseEntity<String> saveMenu( MenuDto menuDto) {
        return null;
    }
    @GetMapping("/")
    public ResponseEntity<String> getMenu( MenuDto menuDto) {
        return null;
    }
    @PatchMapping("/")
    public ResponseEntity<String> updateMenu( MenuDto menuDto) {
        return null;
    }
    @DeleteMapping("/")
    public ResponseEntity<String> deleteMenu( MenuDto menuDto) {
        return null;
    }
}
