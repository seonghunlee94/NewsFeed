package org.example.prepurchase.domain.user.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.prepurchase.domain.user.application.RedisService;
import org.example.prepurchase.domain.user.application.UserService;
import org.example.prepurchase.domain.user.domain.Users;
import org.example.prepurchase.domain.user.dto.LoginRequestDto;
import org.example.prepurchase.domain.user.dto.SignupRequestDto;
import org.example.prepurchase.domain.user.exception.DuplicateException;
import org.example.prepurchase.global.error.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RedisService redisService;

    @Autowired
    public UserController(UserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@Valid @RequestBody SignupRequestDto newUser) {
        try {
            Users signedUpUser = userService.signUpUser(newUser);
            signedUpUser.setPassword(null);
            return ResponseEntity.ok("회원가입되었습니다.");
        } catch (DuplicateException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res) {
        try {
            userService.login(loginRequestDto, res);
            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        redisService.delValues(request.getHeader("username"));
        return ResponseEntity.ok().body("로그아웃 성공!");
    }

}
