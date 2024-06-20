package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * 유저디테일을 커스텀 한 클래스
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * 생성자 매서드
     * @param user
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }
    /**
     * 해당 유저의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(() -> "ROLE_USER");
        return authorities;
    }

    /**
     * 비밀번호
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }


    /**
     * 아이디
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    }



