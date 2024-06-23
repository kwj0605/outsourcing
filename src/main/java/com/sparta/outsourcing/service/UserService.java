package com.sparta.outsourcing.service;


import com.sparta.outsourcing.dto.ProfileDto;
import com.sparta.outsourcing.dto.UserDto;
import com.sparta.outsourcing.dto.LoginRequestDto;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.exception.AlreadySignupException;
import com.sparta.outsourcing.repository.UserRepository;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final JwtService jwtService;

    public ResponseEntity<String> signUp(UserDto userDto) {

        Optional<User> checkUsername = userRepository.findByUsername(userDto.getUsername());
        if (checkUsername.isPresent()) {
            throw new AlreadySignupException(messageSource.getMessage(
                    "already.exist", null, "중복된 사용자가 존재합니다.", Locale.getDefault()
            ));
        }
        User user = new User(
                userDto.getUsername(), bCryptPasswordEncoder.encode(userDto.getPassword()),
                userDto.getNickname(), userDto.getUserinfo(), userDto.getRole()
        );
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("가입 완료");
    }

    public ResponseEntity<ProfileDto> getProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            ProfileDto profileDto = new ProfileDto(user.get().getNickname(), user.get().getUserinfo());
            return ResponseEntity.status(HttpStatus.OK).body(profileDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Transactional
    public ResponseEntity<String> updateProfile(Long userId, User user, ProfileDto profileDto) {

        ProfileDto newProfileDto;

        if (user.getRole().equals(UserRoleEnum.USER)) {
            if(!validateUser(userId,user.getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다른 유저를 수정할 수 없습니다.");
            }
            if(validatePassword(user,profileDto.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("최근 3번안에 사용한 비밀번호는 사용할 수 없습니다.");
            }
            if(bCryptPasswordEncoder.matches(user.getPassword(),profileDto.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재와 같은 비밀번호는 변경할수 없습니다.");
            }

            newProfileDto = new ProfileDto(
                    profileDto.getNickname(), profileDto.getUserinfo(), bCryptPasswordEncoder.encode(profileDto.getPassword())
            );
        } else {
            newProfileDto = new ProfileDto(
                    profileDto.getNickname(), profileDto.getUserinfo(), user.getPassword()
            );
        }

        Optional<User> originUser = userRepository.findById(userId);
        originUser.get().updateProfile(newProfileDto);
        return ResponseEntity.status(HttpStatus.OK).body("수정완료");
    }


    public ResponseEntity<String> signOut(Long userId, User user) {
        if(!validateUser(userId,user.getId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다른 유저를 탈퇴할 수 없습니다.");
        }
        userRepository.findById(userId).get().deleteUser();
        return ResponseEntity.status(HttpStatus.OK).body("탈퇴 완료");
    }

    // 아이디 일치하는지 검증
    private boolean validateUser(Long userId, Long originId) {
        if (!Objects.equals(userId, originId)) {
            return false;
        }
        return true;
    }
    //이전 변경한 비밀번호 검증
    private  boolean validatePassword(User user, String password) {
        List<String> userPassword = user.getDeniedPassword();

        for(int i=0; i<userPassword.size(); i++){
            if(bCryptPasswordEncoder.matches(userPassword.get(i),password)){
                return true;
            }
        }
        return false;
    }

    public void logout(HttpServletResponse response) {
        Cookie accessToken = new Cookie(JwtService.AUTHORIZATION_HEADER, null);
        Cookie refreshToken = new Cookie(JwtService.REFRESH_TOKEN_HEADER, null);

        accessToken.setPath("/");
        refreshToken.setPath("/");

        accessToken.setHttpOnly(true);
        accessToken.setMaxAge(0);
        refreshToken.setHttpOnly(true);
        refreshToken.setMaxAge(0);
        response.addCookie(accessToken);
        response.addCookie(refreshToken);
    }
}
