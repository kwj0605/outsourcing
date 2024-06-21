package com.sparta.outsourcing.entity;

import com.sparta.outsourcing.dto.ProfileDto;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.enums.UserStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;


    private String userinfo;

    @Column(nullable = false)
    private UserStatusEnum status = UserStatusEnum.ACTIVE;

    @Column(nullable = false)
    private UserRoleEnum role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> deniedPassword = new ArrayList<>(3);
    @Column
    private boolean expired = false;

    public User(String username, String password, String nickname, String userinfo, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userinfo = userinfo;
        this.role = role;
    }

    // 프로필 저장
    public void updateProfile(ProfileDto profileDto) {
        this.password = profileDto.getPassword();
        this.nickname = profileDto.getNickname();
        this.userinfo = profileDto.getUserinfo();
        setDeniedPassword(profileDto.getPassword());
    }

    // 최근 변경한 비밀번호 저장
    private void setDeniedPassword(String password){
        if(deniedPassword.size() > 2){
            deniedPassword.remove(0);
        }
        deniedPassword.add(password);
    }

    // 삭제처리
    public void deleteUser() {

    }

    // 리프레쉬 토큰
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}