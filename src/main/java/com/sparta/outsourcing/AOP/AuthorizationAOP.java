//package com.sparta.outsourcing.AOP;
//
//import com.sparta.outsourcing.entity.Authority;
//import com.sparta.outsourcing.security.UserDetailsImpl;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class AuthorizationAOP {
//
//    @Before("execution(* com.sparta.outsourcing.controller.BackOfficeController.*(..))")
//    public void checkAuthorization() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new IllegalStateException("인증되지 않은 사용자입니다.");
//        }
//        Object principal = authentication.getPrincipal();
//        if (!(principal instanceof UserDetailsImpl userDetails)) {
//            throw new IllegalStateException("사용자 정보를 가져올 수 없습니다.");
//        }
//        if (!userDetails.getUser().getAuthority().equals(Authority.ADMIN)) {
//            throw new IllegalStateException("권한이 없습니다.");
//        }
//    }
//}
