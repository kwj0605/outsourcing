package com.sparta.outsourcing.controller;

import com.sparta.outsourcing.dto.ProfileDto;
import com.sparta.outsourcing.dto.ProfileResponseDto;
import com.sparta.outsourcing.dto.UserDto;
import com.sparta.outsourcing.exception.SignUpFailureException;
import com.sparta.outsourcing.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable("userId") Long userId) {
        return userService.getProfile(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable("userId") Long userId, @Valid @RequestBody ProfileDto profileDto) {
        return userService.updateProfile(userId, profileDto);
    }
    @PostMapping("/signout/{userId}")
    public ResponseEntity<String> signOut(@PathVariable("userId") Long userId, HttpServletResponse httpServletResponse) {
        return userService.signOut(userId, httpServletResponse);
    }
}
