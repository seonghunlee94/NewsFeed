package org.example.prepurchase.domain.follow.api;

import jakarta.servlet.http.HttpServletRequest;
import org.example.prepurchase.domain.follow.application.FollowService;
import org.example.prepurchase.domain.follow.dto.FollowDto;
import org.example.prepurchase.global.error.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow")
    public ResponseEntity<String> follow(HttpServletRequest request, @RequestBody FollowDto followUser) {
        String username = request.getHeader("username");

        try {
            followService.follow(username, followUser);
            return ResponseEntity.ok().body("팔로우했습니다.");
        } catch (IllegalArgumentException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.ok().body(errorDto.getMessage());
        }
    }

}
