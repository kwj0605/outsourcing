package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.enums.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class UserDto {
    @NotBlank(message = "필수로 입력해야됩니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{4,10}$" , message = "아이디는 최소 4자, 최대 10자입니다.")
    private String username;
    @NotBlank(message = "필수로 입력해야됩니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-z0-9](?=.*[!@#$%^&*()]).{8,15}$" , message = "비밀번호는 특수문자포함 최소 8자, 최대 15자입니다.")
    private String password;
    @NotBlank(message = "필수로 입력해야됩니다.")
    private String nickname;
    private String userinfo;
    private UserRoleEnum role;

}
