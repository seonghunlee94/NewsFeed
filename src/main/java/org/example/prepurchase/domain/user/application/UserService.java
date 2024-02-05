package org.example.prepurchase.domain.user.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.prepurchase.domain.user.domain.Users;
import org.example.prepurchase.domain.user.dao.UserRepository;
import org.example.prepurchase.domain.user.dto.LoginRequestDto;
import org.example.prepurchase.domain.user.dto.SignupRequestDto;
import org.example.prepurchase.domain.user.dto.UpdateImformationRequestDto;
import org.example.prepurchase.domain.user.exception.DuplicateException;
import org.example.prepurchase.global.auth.UserRoleEnum;
import org.example.prepurchase.global.util.JwtUtil;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RedisService redisService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;

    }


    @Transactional
    public Users signUpUser(SignupRequestDto newUserDto) {
        // 이메일 중복 체크
        Users existingEmail = userRepository.findByEmail(newUserDto.getEmail());
        if (existingEmail != null) {
            throw new DuplicateException("Duplicate email address");
        }

        Users existingUser = userRepository.findByUsername(newUserDto.getUsername());
        if (existingUser != null) {
            throw new DuplicateException("Duplicate username");
        }

        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(newUserDto.getPassword());

        // 회원가입
        Users newUser = new Users(
                newUserDto.getUsername(),
                newUserDto.getEmail(),
                encodedPassword,
                newUserDto.getGreeting(),
                newUserDto.getProfileImage(),
                UserRoleEnum.USER
        );

        return userRepository.save(newUser);
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("등록된 사용자가 없습니다.");
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createAccessToekn(user.getUsername(), user.getRole());
        String refreshToken = jwtUtil.createRefreshToekn(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res, JwtUtil.AUTHORIZATION_HEADER);
        jwtUtil.addJwtToCookie(refreshToken, res, JwtUtil.REFRESH_HEADER);

        // redis에 데이터 넣기.
        redisService.setValues(refreshToken, username);
    }

    public void patchInformation(String username, UpdateImformationRequestDto updateUser) {
        Users user = userRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("없는 사용자입니다.");
        }

        // 변경이 없을 시. flag = False
        Boolean flag = false;

        if (!user.getGreeting().equals(updateUser.getGreeting())) {
            user.setGreeting(updateUser.getGreeting());
            flag = true;
        }

        if (!user.getProfileImage().equals(updateUser.getProfileImage())) {
            user.setProfileImage(updateUser.getProfileImage());
            flag = true;
        }

        if (!flag) {
            throw new IllegalArgumentException("변경된 정보가 없습니다.");
        }
        userRepository.save(user);

    }

    public void patchPassword(String username, UpdateImformationRequestDto updateUser) {

        Users user = userRepository.findByUsername(username);
        String password = updateUser.getPassword();
        String updatePassowrd = updateUser.getUpdatePassword();


        if (user == null) {
            throw new IllegalArgumentException("없는 사용자입니다.");
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(updatePassowrd);

        user.setPassword(encodedPassword);

        userRepository.save(user);

    }

}
