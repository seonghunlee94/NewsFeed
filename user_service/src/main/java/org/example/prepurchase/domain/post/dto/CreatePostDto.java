package org.example.prepurchase.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {

    private String userId;

    @NotNull
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotNull
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
