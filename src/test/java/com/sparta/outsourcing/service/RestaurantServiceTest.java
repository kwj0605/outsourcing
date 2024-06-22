//package com.sparta.outsourcing.service;
//
//import com.sparta.outsourcing.OutsourcingApplication;
//import com.sparta.outsourcing.dto.MenuDto;
//import com.sparta.outsourcing.dto.RestaurantDto;
//import com.sparta.outsourcing.entity.Menu;
//import com.sparta.outsourcing.entity.Restaurant;
//import com.sparta.outsourcing.entity.User;
//import com.sparta.outsourcing.enums.UserRoleEnum;
//import com.sparta.outsourcing.repository.MenuRepository;
//import com.sparta.outsourcing.repository.RestaurantRepository;
//import com.sparta.outsourcing.security.UserDetailsImpl;
//import org.junit.jupiter.api.*;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ContextConfiguration(classes = OutsourcingApplication.class)
//@ActiveProfiles("test")
//public class RestaurantServiceTest {
//
//    @Mock
//    private RestaurantRepository restaurantRepository;
//
//    @Mock
//    private MenuRepository menuRepository;
//
//    @InjectMocks
//    private RestaurantService restaurantService;
//
//    private RestaurantDto restaurantDto;
//    private MenuDto menuDto;
//    private Restaurant restaurant;
//
//    private User user;
//
//    @BeforeEach
//    public void setup() {
//        restaurantDto = new RestaurantDto("Test Restaurant", "Test Restaurant Info", "123-456-7890");
//        restaurant = new Restaurant("Restaurant", "Test Restaurant Info", "123-456-7890");
//        menuDto = new MenuDto(restaurant, "Test Menu", 10000L);
//
//        user = new User("username", "password", "nickname", "userinfo", UserRoleEnum.USER);
//    }
//
//    private void setupSecurityContext(User user_) {
//        UserDetailsImpl userDetails = new UserDetailsImpl(user_);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    @Test
//    public void testAddRestaurant() {
//
//        setupSecurityContext(user);
//        when(restaurantRepository.save(any())).thenReturn(restaurant);
//
//        ResponseEntity<String> responseEntity = restaurantService.addRestaurant(restaurantDto);
//
//        assertEquals("식당이 등록되었습니다.", responseEntity.getBody());
//        assertEquals(200, responseEntity.getStatusCodeValue());
//
//        verify(restaurantRepository, times(1)).save(any());
//    }
//
//    @Test
//    public void testDeleteRestaurant() {
//        Long restaurantId = 1L;
//        Restaurant restaurant = new Restaurant("Test Restaurant", "Test Restaurant Info", "123-456-7890");
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
//
//        ResponseEntity<String> responseEntity = restaurantService.deleteRestaurant(restaurantId);
//
//        assertEquals("식당 정보가 삭제되었습니다.", responseEntity.getBody());
//        assertEquals(200, responseEntity.getStatusCodeValue());
//
//        verify(restaurantRepository, times(1)).delete(ArgumentMatchers.any());
//    }
//
//    @Test
//    public void testAddMenuToRestaurant() {
//        Long restaurantId = 1L;
//        Restaurant restaurant = new Restaurant("Test Restaurant", "Test Restaurant Info", "123-456-7890");
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
//
//        ResponseEntity<String> responseEntity = restaurantService.addMenuToRestaurant(restaurantId, menuDto);
//
//        assertEquals("메뉴가 성공적으로 등록되었습니다.", responseEntity.getBody());
//        assertEquals(200, responseEntity.getStatusCodeValue());
//
//        verify(menuRepository, times(1)).save(any());
//        verify(restaurantRepository, times(1)).save(any());
//    }
//
//    @Test
//    public void testGetRestaurant() {
//        Long restaurantId = 1L;
//        Restaurant restaurant = new Restaurant("Test Restaurant", "Test Restaurant Info", "123-456-7890");
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
//
//        ResponseEntity<RestaurantDto> responseEntity = restaurantService.getRestaurant(restaurantId);
//
//        assertEquals("Test Restaurant", responseEntity.getBody().getRestaurantName());
//        assertEquals(200, responseEntity.getStatusCodeValue());
//
//        verify(restaurantRepository, times(1)).findById(restaurantId);
//    }
//
//    @Test
//    public void testGetMenuList() {
//        Long restaurantId = 1L;
//        Restaurant restaurant = new Restaurant("Test Restaurant", "Test Restaurant Info", "123-456-7890");
//        List<Menu> menuList = new ArrayList<>();
//        menuList.add(new Menu(restaurant, "Test Menu", 10000L));
//        restaurant.setMenuList(menuList);
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
//
//        ResponseEntity<List<MenuDto>> responseEntity = restaurantService.getMenuList(restaurantId);
//
//        assertEquals(1, responseEntity.getBody().size());
//        assertEquals("Test Menu", responseEntity.getBody().get(0).getMenuName());
//        assertEquals(200, responseEntity.getStatusCodeValue());
//
//        verify(restaurantRepository, times(1)).findById(restaurantId);
//    }
//}
