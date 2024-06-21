package com.sparta.outsourcing.security;

import com.sparta.outsourcing.exception.BlackListedTokenException;
import com.sparta.outsourcing.service.JwtBlacklistService;
import com.sparta.outsourcing.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "blacklist filter")
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    // 요청 들어올때마다 토큰이 블랙리스트에 있는지 확인한다

    private JwtBlacklistService jwtBlacklistService;
    private JwtService jwtService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            log.info("jwtToken : {}", jwtToken);
            if (jwtBlacklistService.isTokenBlacklisted(jwtToken)) {
                log.info("token is blacklisted");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
                throw new BlackListedTokenException();
            }
        }
        chain.doFilter(request, response);
    }
}
