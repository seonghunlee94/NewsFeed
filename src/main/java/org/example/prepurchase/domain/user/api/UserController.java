/*
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
//        return userService.signUpUser(newUser);
        Users signedUpUser = userService.signUpUser(newUser);

        // 패스워드를 클라이언트에게 노출하지 않도록 숨김
        signedUpUser.setPassword(null);

        return signedUpUser;
    }

}*/

package org.example.prepurchase.domain.user.api;

import org.example.prepurchase.domain.user.application.UserService;
import org.example.prepurchase.domain.user.domain.Users;
import org.example.prepurchase.global.error.DuplicateEmailException;
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
    public ResponseEntity<?> signUpUser(@RequestBody Users newUser) {
        try {
            Users signedUpUser = userService.signUpUser(newUser);
            signedUpUser.setPassword(null);
            return ResponseEntity.ok(signedUpUser);
        } catch (DuplicateEmailException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
        }
    }
}
