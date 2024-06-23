package com.sparta.outsourcing.security;

import com.sparta.outsourcing.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";

    public JwtAuthorizationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtService.getTokenFromRequest(request, AUTHORIZATION_HEADER);
        String refreshToken = jwtService.getTokenFromRequest(request, REFRESH_TOKEN_HEADER);

        if (StringUtils.hasText(accessToken)) {
            // JWT 토큰 substring
            accessToken = jwtService.substringToken(accessToken);
            log.info(accessToken);

            // accessToken 만료 & refreshToken 유효한 경우
            // 새로운 accessToken 발급
            if (!jwtService.validateToken(accessToken)) {
                if (refreshToken != null && jwtService.validateToken(refreshToken)){
                    String username = jwtService.extractUsername(refreshToken);
                    String newAccessToken = jwtService.createToken(username);

                    response.setHeader(AUTHORIZATION_HEADER, newAccessToken);
                    jwtService.addJwtToCookie(newAccessToken, response, true);
                } else {
                    log.error("Token Error");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }

            String username = jwtService.getUsernameFromRequest(request);

            try {
                setAuthentication(username);
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    // 인증 처리
    private void setAuthentication(String userId) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(userId);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String userId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
