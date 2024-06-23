package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.CustomUserDetails;
import com.sparta.outsourcing.dto.ProfileDto;
import com.sparta.outsourcing.dto.UserDto;
import com.sparta.outsourcing.dto.LoginRequestDto;
import com.sparta.outsourcing.exception.SignUpFailureException;
import com.sparta.outsourcing.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid  @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError fieldError : fieldErrors) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
                errorMessage.append(fieldError.getDefaultMessage()).append(" ");
            }
            throw new SignUpFailureException(errorMessage.toString().trim());
        }
        return userService.signUp(userDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        userService.logout(response);
        return ResponseEntity.ok("로그아웃되었습니다.");
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
