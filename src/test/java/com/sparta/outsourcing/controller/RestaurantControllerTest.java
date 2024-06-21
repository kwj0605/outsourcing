package com.sparta.outsourcing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.dto.MenuDto;
import com.sparta.outsourcing.dto.RestaurantDto;
import com.sparta.outsourcing.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;

    private RestaurantDto restaurantDto;
    private MenuDto menuDto;

    @BeforeEach
    public void setup() {
        // Mock 데이터 초기화
        MockitoAnnotations.openMocks(this);
        restaurantDto = new RestaurantDto("Test Restaurant", "Test Restaurant Info", "123-456-7890");
    }

    @Test
    @WithMockUser
    public void testAddRestaurant() throws Exception {
        // Mock Service 메서드 호출 설정
        //when(restaurantService.addRestaurant(any())).thenReturn(ResponseEntity.ok("Restaurant added successfully"));

        // MockMvc를 이용한 POST 요청 테스트
        mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Restaurant added successfully"));
    }

//    @Test
//    public void testDeleteRestaurant() throws Exception {
//        Long restaurantId = 1L;
//        // Mock Service 메서드 호출 설정
//        when(restaurantService.deleteRestaurant(eq(restaurantId))).thenReturn(ResponseEntity.ok("Restaurant deleted successfully"));
//
//        // MockMvc를 이용한 DELETE 요청 테스트
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/restaurant/{restaurantId}", restaurantId))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Restaurant deleted successfully"));
//    }
//
//    @Test
//    public void testGetRestaurant() throws Exception {
//        Long restaurantId = 1L;
//        // Mock Service 메서드 호출 설정
//        when(restaurantService.getRestaurant(eq(restaurantId))).thenReturn(ResponseEntity.ok(restaurantDto));
//
//        // MockMvc를 이용한 GET 요청 테스트
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/{restaurantId}", restaurantId))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.restaurantName").value(restaurantDto.getRestaurantName()));
//    }
//
//    @Test
//    public void testGetMenuList() throws Exception {
//        Long restaurantId = 1L;
//        // Mock Service 메서드 호출 설정
//        when(restaurantService.getMenuList(eq(restaurantId))).thenReturn(ResponseEntity.ok(Collections.singletonList(menuDto)));
//
//        // MockMvc를 이용한 GET 요청 테스트
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurant/{restaurantId}/menu", restaurantId))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].menuName").value(menuDto.getMenuName()));
//    }
//
//    @Test
//    public void testAddMenu() throws Exception {
//        Long restaurantId = 1L;
//        // Mock Service 메서드 호출 설정
//        when(restaurantService.addMenuToRestaurant(eq(restaurantId), any())).thenReturn(ResponseEntity.ok("Menu added successfully"));
//
//        // MockMvc를 이용한 POST 요청 테스트
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/restaurant/{restaurantId}/menu", restaurantId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(menuDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Menu added successfully"));
//    }
}
