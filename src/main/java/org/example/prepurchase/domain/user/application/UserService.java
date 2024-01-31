package org.example.prepurchase.domain.user.application;

import org.example.prepurchase.domain.user.domain.Users;
import org.example.prepurchase.domain.user.dao.UserRepository;
import org.example.prepurchase.domain.user.dto.UserRequestDto;
import org.example.prepurchase.domain.user.exception.DuplicateEmailException;
import org.example.prepurchase.domain.user.exception.DuplicateUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
                newUserDto.getProfileImage()
        );

        return userRepository.save(newUser);
    }


}
