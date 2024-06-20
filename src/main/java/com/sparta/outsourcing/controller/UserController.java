package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.CustomUserDetails;
import com.sparta.outsourcing.dto.ProfileDto;
import com.sparta.outsourcing.dto.UserDto;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto , UserRoleEnum userRoleEnum) {
        return userService.signUp(userDto, userRoleEnum);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long userId) {
        return userService.getProfile(userId);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable("userId") Long userId,
            CustomUserDetails user ,ProfileDto profileDto) {
        return userService.updateProfile(userId,user.getUser(),profileDto);
    }
    @PostMapping("/{userId}/sign-out")
    public ResponseEntity<String> signOut(@PathVariable("userId") Long userId, CustomUserDetails user) {
        return userService.signOut(userId, user.getUser());
    }
}
