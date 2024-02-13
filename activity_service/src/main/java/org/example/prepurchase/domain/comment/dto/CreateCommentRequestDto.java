package org.example.prepurchase.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequestDto {


    private Long postId;

    private String userId;

    private Long parentId;

    @NotNull
    private String content;

    @NotNull
    private String title;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
