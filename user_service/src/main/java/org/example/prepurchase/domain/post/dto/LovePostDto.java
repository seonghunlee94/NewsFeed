package org.example.prepurchase.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LovePostDto {

    @NotNull
    private Long postId;

    @NotNull
    private String userId;

    private LocalDateTime createTime;
}
