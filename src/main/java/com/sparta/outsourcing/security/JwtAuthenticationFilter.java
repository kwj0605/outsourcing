package com.sparta.outsourcing.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.dto.login.LoginRequestDto;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.repository.UserRepository;
import com.sparta.outsourcing.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.sparta.outsourcing.enums.UserStatusEnum.DENIED;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try{
            LoginRequestDto loginRequestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            User user = userRepository.findByUserName(loginRequestDto.getUsername()).orElse(null);
            if(null != user) {
                if(user.getStatus() == DENIED){
                    log.info("삭제된 사용자입니다");
                    throw new IllegalArgumentException("삭제된 사용자입니다.");
                }
            }

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword(),
                            null

                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String userId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        // 토큰 생성
        String token = jwtService.createToken(userId);
        String refreshToken = jwtService.createRefreshToken(userId);
        // refresh token 유저에 저장
        User user = userRepository.findByUserName(userId).orElseThrow(NullPointerException::new);
        user.setRefreshToken(refreshToken);

        userRepository.save(user);

        // 헤더에 토큰 저장
        response.setHeader("Authorization", token);
        response.setHeader("RefreshToken", refreshToken);
        // 로그인 성공 메세지 반환
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString("로그인 성공!"));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}
