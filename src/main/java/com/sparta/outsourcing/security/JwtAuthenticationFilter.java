package com.sparta.outsourcing.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.dto.LoginRequestDto;
import com.sparta.outsourcing.dto.TokenDto;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.AuthEnum;
import com.sparta.outsourcing.enums.UserStatusEnum;
import com.sparta.outsourcing.repository.UserRepository;
import com.sparta.outsourcing.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import org.springframework.stereotype.Component;

import static com.sparta.outsourcing.enums.UserStatusEnum.DENIED;

@Order(Integer.MIN_VALUE)
@Component
@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try{
            loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElse(null);
            if(null != user) {
                if(user.getStatus() == DENIED){
                    log.info("삭제된 사용자입니다");
                    throw new IllegalArgumentException("삭제된 사용자입니다.");
                }
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getUsername(),loginRequestDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        TokenDto jwtToken = jwtService.createToken(authResult);
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        user.get().updateRefreshToken(jwtToken.getRefreshToken());
        userRepository.save(user.get());
        response.setHeader(AuthEnum.ACCESS_TOKEN.getValue(), jwtToken.getAccessToken());
        response.setHeader(AuthEnum.REFRESH_TOKEN.getValue(), jwtToken.getRefreshToken());
        response.setStatus(200);
        // 로그인 성공 메세지 반환
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString("로그인 성공!"));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);

        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("로그인에 실패하였습니다");
    }
}
