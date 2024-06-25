package com.sparta.outsourcing.service;


import com.sparta.outsourcing.dto.ProfileDto;
import com.sparta.outsourcing.dto.ProfileResponseDto;
import com.sparta.outsourcing.dto.UserDto;
import com.sparta.outsourcing.entity.Menu;
import com.sparta.outsourcing.entity.Order;
import com.sparta.outsourcing.entity.Restaurant;
import com.sparta.outsourcing.entity.Review;
import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.enums.UserRoleEnum;
import com.sparta.outsourcing.enums.StatusEnum;
import com.sparta.outsourcing.exception.AlreadySignupException;
import com.sparta.outsourcing.exception.UserNotFoundException;
import com.sparta.outsourcing.repository.OrderRepository;
import com.sparta.outsourcing.repository.RestaurantRepository;
import com.sparta.outsourcing.repository.ReviewRepository;
import com.sparta.outsourcing.repository.UserRepository;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;


    public ResponseEntity<String> signUp(UserDto userDto, Long roleId) {

        Optional<User> checkUsername = userRepository.findByUsername(userDto.getUsername());
        if (checkUsername.isPresent()) {
            throw new AlreadySignupException(messageSource.getMessage(
                    "already.exist", null, "중복된 사용자가 존재합니다.", Locale.getDefault()
            ));
        }
        User user = new User(
                userDto.getUsername(), bCryptPasswordEncoder.encode(userDto.getPassword()),
                userDto.getNickname(), userDto.getUserinfo());
        if(roleId == 1L){

            user.setRole(UserRoleEnum.ROLE_USER);
        }else if(roleId == 2L){
            user.setRole(UserRoleEnum.ROLE_ADMIN);
        }else {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 권한입니다.");}

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("가입 완료\n"+"⣿⣿⣿⣿⣿⣿⣿⠟⠋⠉⠁⠈⠉⠙⠻⢿⡿⠿⠛⠋⠉⠙⠛⢿⣿⣿⣿⣿⣿⣿\n"+
                "⣿⣿⣿⣿⣿⠟⠁⠀⠀⢀⣀⣀⣀⣀⡀⠀⢆⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿\n"+
                "⣿⣿⣿⣿⠃⠀⠀⠠⠊⠁⠀⠀⠀⠀⠈⠑⠪⡖⠒⠒⠒⠒⠒⠒⠶⠛⠿⣿⣿⣿\n"+
                "⣿⣿⡿⡇⠀⠀⠀⠀⠀⠀⡠⢔⡢⠍⠉⠉⠩⠭⢑⣤⣔⠲⠤⠭⠭⠤⠴⢊⡻⣿\n"+
                "⡿⠁⢀⠇⠀⠀⠀⣤⠭⠓⠊⣁⣤⠂⠠⢀⡈⠱⣶⣆⣠⣴⡖⠁⠂⣀⠈⢷⣮⣹\n"+
                "⠁⠀⠀⠀⠀⠀⠀⠈⠉⢳⣿⣿⣿⡀⠀⠀⢀⣀⣿⡿⢿⣿⣇⣀⣥⣤⠤⢼⣿⣿\n"+
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡟⠑⠚⢹⡟⠉⣑⠒⢺⡇⡀⠀⡹⠀⠀⣀⣴⣽⣿⣿\n"+
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⣿⠒⠉⠀⠀⢠⠃⠈⠙⠻⣍⠙⢻⡻⣿⣿⣿\n"+
                "⠀⠀⠀⠀⠀⠀⠀⠀⣀⣘⡄⠀⠀⢸⡇⠀⠀⠀⠘⡇⠀⠀⠀⠘⡄⠀⢱⢸⣿⣿\n"+
                "⠀⠀⠀⠀⠠⡀⠀⠾⣟⣻⣛⠷⣶⣼⣥⣀⣀⣀⠀⢧⠀⠀⠀⠠⣧⣀⣼⣴⢽⣿\n"+
                "⠀⠀⠀⠀⠀⠈⠉⠁⠀⠹⡙⠛⠷⣿⣭⣯⣭⣟⣛⣿⣿⣿⣛⣛⣿⣭⣭⣾⣿⣿\n"+
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⡀⠀⠀⣇⠀⠉⠉⠉⡏⠉⠙⠛⠛⡿⣻⣯⣷⣿⣿⣿\n"+
                "⣶⣤⣀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⢸⠀⠀⠀⡸⠁⣠⣴⣶⣿⣿⣿⣿⣿⣿⣿⣿\n"+
                "⣿⣿⣿⣿⣶⣶⣦⣤⣤⣤⣷⣤⣄⣈⣆⣤⣤⣧⣶⣷⣿⡻⣿⣿⣿⣿⣿⣿⣿⣿\n"+
                "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣾⢿⣿⣿⣿⣿⣿⣿");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ProfileResponseDto> getProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자가 없습니다!");
        }
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(user.get().getNickname(), user.get().getUserinfo());
        return ResponseEntity.status(HttpStatus.OK).body(profileResponseDto);
    }

    @Transactional
    public ResponseEntity<String> updateProfile(Long userId, ProfileDto profileDto, User user) {
        ProfileDto newProfileDto;

        if (user.getRole().equals(UserRoleEnum.ROLE_USER) || user.getRole().equals(UserRoleEnum.ROLE_ADMIN)) {
            if(!user.getId().equals(userId)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다른 유저를 수정할 수 없습니다.");
            }
            if(validatePassword(profileDto.getPassword(),user)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("최근 3번안에 사용한 비밀번호는 사용할 수 없습니다.");
            }
            if(bCryptPasswordEncoder.matches(profileDto.getPassword(),user.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재와 같은 비밀번호는 변경할수 없습니다.");
            }

            newProfileDto = new ProfileDto(
                    profileDto.getNickname(), profileDto.getUserinfo(), bCryptPasswordEncoder.encode(profileDto.getPassword()));
        } else {
            newProfileDto = new ProfileDto(
                    profileDto.getNickname(), profileDto.getUserinfo(), user.getPassword());
        }

        Optional<User> originUser = userRepository.findById(userId);
        if (originUser.isEmpty()) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다.");
        }
        originUser.get().updateProfile(newProfileDto);
        return ResponseEntity.status(HttpStatus.OK).body("수정완료");
    }


    public ResponseEntity<String> deleteUser(Long userId, User user) {

        Optional<User> originUser = userRepository.findById(userId);
        if (originUser.isEmpty() || originUser.get().getStatus().equals(StatusEnum.DENIED)) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다.");
        }

        if(!validateUser(user.getId(), originUser.get().getId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("다른 유저를 탈퇴할 수 없습니다.");
        }
        if(user.getRole().equals(UserRoleEnum.ROLE_USER)){
            List<Order> orders = orderRepository.findByUserId(userId);
            orders.forEach(order -> {
                order.delete();
                order.getReviews().forEach(Review::delete);
            });
        }
        if(user.getRole().equals(UserRoleEnum.ROLE_ADMIN)){
            List<Restaurant> restaurants = restaurantRepository.findByUserId(userId);
            restaurants.forEach(restaurant -> {
                restaurant.delete();
                restaurant.getReviews().forEach(Review::delete);
            });
        }

        originUser.get().setStatus(StatusEnum.DENIED);
        originUser.get().setExpired(false);
        originUser.get().deleteUser();
        userRepository.save(originUser.get());
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
    private boolean validatePassword(String password,User user) {
        List<String> userPassword = user.getDeniedPassword();
        for (int i = 0; i < userPassword.size(); i++) {
            if (bCryptPasswordEncoder.matches(password, userPassword.get(i))) {
                return true;
            }
        }
        return false;
    }
}