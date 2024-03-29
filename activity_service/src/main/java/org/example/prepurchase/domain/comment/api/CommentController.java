package org.example.prepurchase.domain.comment.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.prepurchase.domain.comment.application.CommentService;
import org.example.prepurchase.domain.comment.dto.CreateCommentRequestDto;
import org.example.prepurchase.global.error.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createComment(HttpServletRequest request, @Valid @RequestBody CreateCommentRequestDto createComment) {



        try {
            commentService.createComment(request, createComment);
            return ResponseEntity.ok().body("댓글을 생성했습니다.");
        } catch (IllegalArgumentException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.ok().body(errorDto.getMessage());
        }

    }

    @PostMapping("/love")
    public ResponseEntity<String> loveComment(HttpServletRequest request) {

        String id = request.getHeader("id");
        String username = request.getHeader("username");

        try {
            commentService.loveComment(id, username);
            return ResponseEntity.ok().body("댓글을 좋아합니다.");
        } catch(IllegalArgumentException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.ok().body(errorDto.getMessage());
        }
    }

}
