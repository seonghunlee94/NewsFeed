package org.example.prepurchase.domain.user.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.prepurchase.domain.user.application.UserService;
import org.example.prepurchase.domain.user.domain.Users;
import org.example.prepurchase.domain.user.dto.LoginRequestDto;
import org.example.prepurchase.domain.user.dto.UserRequestDto;
import org.example.prepurchase.domain.user.exception.DuplicateEmailException;
import org.example.prepurchase.domain.user.exception.DuplicateUsernameException;
import org.example.prepurchase.global.error.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@Valid @RequestBody UserRequestDto newUser) {
        try {
            Users signedUpUser = userService.signUpUser(newUser);
            signedUpUser.setPassword(null);
            return ResponseEntity.ok("회원가입되었습니다.");
        } catch (DuplicateEmailException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto.getMessage());
        } catch (DuplicateUsernameException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res) {
        try {
            // 로그인 기능을 UserService에 위임
            userService.login(loginRequestDto, res);
            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto.getMessage());
        }
    }
}
