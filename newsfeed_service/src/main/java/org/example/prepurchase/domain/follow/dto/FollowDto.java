package org.example.prepurchase.domain.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {

    @NotNull(message = "toUser는 필수 입력 값입니다.")
    private String toUser;

    @NotNull(message = "fromUser는 필수 입력 값입니다.")
    private String fromUser;

    private LocalDateTime createTime;



}
