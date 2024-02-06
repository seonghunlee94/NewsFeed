package org.example.prepurchase.domain.post.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Setter
public class PostLove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Posts post;

    @NotNull
    private String UserId;

    private LocalDateTime createTime;


    public PostLove() {
    }

    // Parameterized constructor
    public PostLove(Long id, Posts postId, LocalDateTime createTime) {
        this.id = id;
        this.post = postId;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Posts getPostId() {
        return post;
    }

    public void setPostId(Posts post) {
        this.post = post;
    }


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
