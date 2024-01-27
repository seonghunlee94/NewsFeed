package org.example.prepurchase.domain.user.application;

import org.example.prepurchase.domain.user.domain.Users;
import org.example.prepurchase.domain.user.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users signUpUser(Users newUser) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(newUser.getEmail()) != null) {
            // 중복된 이메일이 이미 존재할 경우 예외 처리 또는 다른 로직 추가
            throw new RuntimeException("Duplicate email address");
        }

        // 패스워드 암호화
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // 회원가입
        return userRepository.save(newUser);
    }
}