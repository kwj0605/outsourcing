package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.MenuDto;
import com.sparta.outsourcing.entity.Menu;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.StatusEnum;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    public ResponseEntity<String> addMenu(Long restaurnatId, MenuDto menuDto, User user) {
        validate(restaurnatId, user.getId());
        Menu menu = new Menu(restaurantRepository.findById(restaurnatId).get(),menuDto.getMenuName(),(menuDto.getPrice()));
        menuRepository.save(menu);
        return ResponseEntity.status(HttpStatus.OK).body("메뉴가 등록되었습니다.");
    }


    public ResponseEntity<String> updateMenu(Long restaurnatId, MenuDto menuDto, User user, Long menuId) {
        validate(restaurnatId, user.getId());
        Menu menu = menuRepository.findByRestaurantIdAndMenuId(restaurnatId,menuId).get();
        menu.update(menuDto);
        menuRepository.save(menu);
        return ResponseEntity.status(HttpStatus.OK).body("메뉴가 등록되었습니다.");
    }

    public ResponseEntity<List<MenuDto>> getMenus(Long restaurnatId) {
        if(menuRepository.findByRestaurantId(restaurnatId).isEmpty()) {
            throw new RuntimeException("존재하지 않습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(menuRepository.findByRestaurantId(restaurnatId));
    }

    public ResponseEntity<String> deleteMenu(Long restaurnatId, User user, Long menuId){
        validate(restaurnatId, user.getId());
        if(menuRepository.findById(menuId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("메뉴가 없습니다.");
        }

        Menu menu = menuRepository.findByRestaurantIdAndMenuId(restaurnatId,menuId).get();
        menu.delete();
        menuRepository.save(menu);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

    public void validate(Long restaurnatId, Long userId) {
        if (restaurantRepository.findById(restaurnatId).isEmpty()) {
            throw new RuntimeException("식당을 찾을수 없습니다.");
        }
        if (!restaurantRepository.findById(restaurnatId).get().getUser().getId().equals(userId)) {
            throw new RuntimeException("사용자가 올비르지 않습니다.");
        }

    }
}