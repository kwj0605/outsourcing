package com.sparta.outsourcing.service;

import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.exception.TokenNotFoundException;
import com.sparta.outsourcing.exception.UserIdNotFoundException;
import com.sparta.outsourcing.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthService")
@Component
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtBlacklistService jwtBlacklistService;

    // refresh token
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws TokenNotFoundException {

        String refreshToken = request.getHeader("RefreshToken").substring(7);

        // accessToken 유효성 확인
        if(jwtService.validateToken(refreshToken)){
            String userId = jwtService.extractUserId(refreshToken);
            User user = userRepository.findByUserName(userId).orElseThrow(UserIdNotFoundException::new);

            // accessToken 새로 발급
            String newAccessToken = jwtService.createToken(user.getUsername());
            log.info("새로운 access token : {}", newAccessToken);
            //refreshToken 새로 발급
            String newRefreshToken = jwtService.createRefreshToken(user.getUsername());
            log.info("새로운 refresh token : {}", newRefreshToken);

            user.setRefreshToken(newRefreshToken);
            response.setHeader("Authorization", newAccessToken);
            response.setHeader("RefreshToken", newRefreshToken);
        }
    }
}
