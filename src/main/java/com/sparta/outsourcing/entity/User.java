package com.sparta.outsourcing.entity;

import com.sparta.outsourcing.dto.ProfileDto;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.enums.UserStatusEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    private String userinfo;

    @Setter
    @Column(nullable = false)
    private UserStatusEnum status = UserStatusEnum.ACTIVE;

    @Column(nullable = false)
    private UserRoleEnum role;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> deniedPassword = new ArrayList<>(3);

    private String refreshtoken;

    @Setter
    @Column
    private boolean expired = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

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
    //토큰 값 초기화
    public void updateRefreshToken(String refreshToken) {
        this.refreshtoken = refreshToken;
    }
    // 삭제처리
    public void deleteUser() {
    }

}