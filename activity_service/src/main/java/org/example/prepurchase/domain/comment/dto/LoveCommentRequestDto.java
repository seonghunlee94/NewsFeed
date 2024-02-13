package org.example.prepurchase.domain.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoveCommentRequestDto {

    private Long commentId;

    private String userId;

    private LocalDateTime createTime;

}
