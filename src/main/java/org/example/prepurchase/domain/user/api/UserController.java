package org.example.prepurchase.domain.user.api;

import org.example.prepurchase.domain.user.application.UserService;
import org.example.prepurchase.domain.user.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 기존 코드 생략

    // 회원가입 엔드포인트
    @PostMapping("/signup")
    public Users signUpUser(@RequestBody Users newUser) {
        // 사용자의 회원가입을 처리하고 결과를 반환
        return userService.signUpUser(newUser);
    }
}