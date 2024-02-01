package org.example.prepurchase.domain.user.application;

import jakarta.servlet.http.HttpServletResponse;
import org.example.prepurchase.domain.user.domain.Users;
import org.example.prepurchase.domain.user.dao.UserRepository;
import org.example.prepurchase.domain.user.dto.LoginRequestDto;
import org.example.prepurchase.domain.user.dto.UserRequestDto;
import org.example.prepurchase.domain.user.exception.DuplicateEmailException;
import org.example.prepurchase.domain.user.exception.DuplicateUsernameException;
import org.example.prepurchase.global.auth.UserRoleEnum;
import org.example.prepurchase.global.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }


    @Transactional
    public Users signUpUser(UserRequestDto newUserDto) {
        // 이메일 중복 체크
        Users existingEmail = userRepository.findByEmail(newUserDto.getEmail());
        if (existingEmail != null) {
            throw new DuplicateEmailException("Duplicate email address");
        }

        Users existingUser = userRepository.findByUsername(newUserDto.getUsername());
        if (existingUser != null) {
            throw new DuplicateUsernameException("Duplicate username");
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
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
    }


}
