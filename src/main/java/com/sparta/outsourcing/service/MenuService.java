package com.sparta.outsourcing.service;

import com.sparta.outsourcing.dto.MenuDto;
import com.sparta.outsourcing.entity.Menu;
import com.sparta.outsourcing.entity.Restaurant;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.exception.InvalidAccessException;
import com.sparta.outsourcing.repository.MenuRepository;
import com.sparta.outsourcing.repository.RestaurantRepository;
import com.sparta.outsourcing.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MessageSource messageSource;

    public ResponseEntity<String> addMenuToRestaurant(Long restaurantId, MenuDto menuDto) {

        Restaurant restaurant = getRestaurant(restaurantId);
        validateUserAccess(restaurant.getUser());
        Menu menu = new Menu(restaurant, menuDto.getMenuName(), menuDto.getPrice());
        restaurant.getMenuList().add(menu);

        menuRepository.save(menu);
        restaurantRepository.save(restaurant);

        return ResponseEntity.ok("메뉴가 성공적으로 등록되었습니다.");
    }

    public ResponseEntity<String> updateMenu(Long menuId, MenuDto menuDto) {
        Menu menu = getMenu(menuId);
        validateUserAccess(menu.getRestaurant().getUser());
        menu.setMenuName(menuDto.getMenuName());
        menu.setPrice(menuDto.getPrice());

        menuRepository.save(menu);

        return ResponseEntity.ok("메뉴 정보가 수정되었습니다.");
    }

    public ResponseEntity<String> deleteMenu(Long menuId) {
        Menu menu = getMenu(menuId);

        validateUserAccess(menu.getRestaurant().getUser());

        menuRepository.delete(menu);

        return ResponseEntity.ok("메뉴 정보가 삭제되었습니다.");
    }

    public ResponseEntity<List<MenuDto>> getMenuList(Long restaurantId) {
        Restaurant restaurant = getRestaurant(restaurantId);
        List<MenuDto> menuDtoList = restaurant.getMenuList().stream()
                .map(menu -> new MenuDto(menu.getMenuName(), menu.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(menuDtoList);
    }

    private Restaurant getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new InvalidAccessException(
                        messageSource.getMessage("restaurant.not.found", null, "식당 정보를 찾을 수 없습니다.", Locale.getDefault())));
    }

    private Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new InvalidAccessException(
                        messageSource.getMessage("menu.not.found", null, "메뉴 정보를 찾을 수 없습니다.", Locale.getDefault())));
    }

    private void validateUserAccess(User restaurantUser) {
        User currentUser = getCurrentUser();
        if (!currentUser.getUsername().equals(restaurantUser.getUsername()) && !currentUser.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new InvalidAccessException(
                    messageSource.getMessage("invalid.access", null, "적합하지 않은 접근입니다.", Locale.getDefault()));
        }
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증되지 않은 사용자입니다.");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl)) {
            throw new IllegalStateException("사용자 정보를 가져올 수 없습니다.");
        }
        return ((UserDetailsImpl) principal).getUser();
    }
}
