package org.example.prepurchase.domain.newsfeed.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.prepurchase.global.config.NewsFeedType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "newsFeeds")
@EntityListeners(value = {AuditingEntityListener.class})
public class NewsFeeds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String senderId;

    @NotNull
    private String receiverId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NewsFeedType serviceType;

    private String postName;

    private Boolean isRead;

    private LocalDateTime createTime;




}
