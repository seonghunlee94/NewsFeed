package org.example.prepurchase.domain.user.application;

import org.example.prepurchase.domain.user.domain.Users;
import org.example.prepurchase.domain.user.dao.UserRepository;
import org.example.prepurchase.domain.user.dto.UserRequestDto;
import org.example.prepurchase.global.error.DuplicateEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public Users signUpUser(UserRequestDto newUserDto) {
        // 이메일 중복 체크
        Users existingUser = userRepository.findByEmail(newUserDto.getEmail());
        if (existingUser != null) {
            throw new DuplicateEmailException("Duplicate email address");
        }

        // 회원가입
        Users newUser = new Users(
                newUserDto.getName(),
                newUserDto.getEmail(),
                newUserDto.getPassword(),
                newUserDto.getGreeting(),
                newUserDto.getProfileImage()
        );

        return userRepository.save(newUser);
    }


}
