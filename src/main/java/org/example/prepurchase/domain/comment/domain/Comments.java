package org.example.prepurchase.domain.comment.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.prepurchase.domain.post.domain.Posts;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Posts post;

    @NotNull
    private String userId;

    private String content;

    private Long parentId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "comment")
    private List<CommentLove> commentLoveEntities;

}
