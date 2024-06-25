package com.sparta.outsourcing.config;

import com.sparta.outsourcing.exception.CustomAuthenticationEntryPoint;
import com.sparta.outsourcing.repository.UserRepository;
import com.sparta.outsourcing.security.JwtAuthenticationFilter;
import com.sparta.outsourcing.security.JwtAuthorizationFilter;
import com.sparta.outsourcing.security.UserDetailsServiceImpl;
import com.sparta.outsourcing.service.AuthService;
import com.sparta.outsourcing.service.JwtService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableJpaAuditing
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;

    public WebSecurityConfig(JwtService jwtService, UserDetailsServiceImpl userDetailsService,
                             AuthenticationConfiguration authenticationConfiguration, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.userRepository = userRepository;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userRepository,authenticationManager(authenticationConfiguration));
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthService authService) throws Exception {
        /**
         * csrf 비활성화
         */
        http.csrf((csrf) -> csrf.disable());

        /**
         * JWT 방식을 사용하기 위한 설정
         */
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        /**
         * 요청 권한 설정
         */
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/api/user/signup").permitAll()
                        .requestMatchers("/api/user/login").permitAll()
                        .requestMatchers("/api/user/**").permitAll()
                        // 서버 단에서 에러가 발생시 아래 url이 에러창을 띄워준다
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
        );

        http.exceptionHandling(auth -> {
            auth.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        });

        /**
         * 로그아웃 설정
         */
        http.logout(auth -> auth
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler(authService)
                .logoutSuccessHandler(
                        (((request, response, authentication) -> SecurityContextHolder.clearContext()))));

        /**
         * 필터 관리
         */
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }
}
