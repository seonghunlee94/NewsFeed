package org.example.prepurchase.domain.newsfeed.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsFeedRequestDto {

    @NotNull
    private String senderId;

    @NotNull
    private String receiverId;

    @NotNull
    private String serviceType;

    private String postName;

    private Boolean isRead;

    private LocalDateTime createTime;

}
