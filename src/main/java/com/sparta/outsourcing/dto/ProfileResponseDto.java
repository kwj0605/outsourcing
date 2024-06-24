package com.sparta.outsourcing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {
    private String nickname;
    private String userinfo;

    public ProfileResponseDto(String nickname, String userinfo) {
        this.nickname = nickname;
        this.userinfo = userinfo;
    }
}
