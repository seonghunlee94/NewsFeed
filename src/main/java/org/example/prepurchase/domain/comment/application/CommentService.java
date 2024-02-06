package org.example.prepurchase.domain.comment.application;

import org.example.prepurchase.domain.comment.dao.CommentLoveRepository;
import org.example.prepurchase.domain.comment.dao.CommentRepository;
import org.example.prepurchase.domain.comment.domain.CommentLove;
import org.example.prepurchase.domain.comment.domain.Comments;
import org.example.prepurchase.domain.comment.dto.CreateCommentRequestDto;
import org.example.prepurchase.domain.post.dao.PostRepository;
import org.example.prepurchase.domain.post.domain.PostLove;
import org.example.prepurchase.domain.post.domain.Posts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLoveRepository commentLoveRepository;


    public CommentService(CommentRepository commentRepository, PostRepository postRepository, CommentLoveRepository commentLoveRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentLoveRepository = commentLoveRepository;
    }

    public void createComment(String idString, String username, CreateCommentRequestDto createComment) {
        Long id = Long.valueOf(idString);
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Optional<Posts> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            throw new IllegalArgumentException("주어진 id에 해당하는 포스트를 찾을 수 없습니다.");
        }

        Posts post = postOptional.get();

        Comments comment = new Comments();

        comment.setPost(post);
        comment.setUserId(username);
        comment.setContent(createComment.getContent());
        comment.setCreateTime(dateTime);
        comment.setUpdateTime(dateTime);

        commentRepository.save(comment);

    }

    public void loveComment(String idString, String username) {

        Long id = Long.valueOf(idString);

        Optional<Comments> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new IllegalArgumentException("주어진 id에 해당하는 댓글을 찾을 수 없습니다.");
        }

        Comments comment = commentOptional.get();

        Optional<CommentLove> checkLove = commentLoveRepository.findCommentLoveByCommentrAndUserId(comment.getId(), username);
        if (!checkLove.isEmpty()) {
            throw new IllegalArgumentException("이미 '좋아요' 한 댓글입니다.");
        }

        CommentLove commentLove = new CommentLove();

        commentLove.setComment(comment);
        commentLove.setUserId(username);
        commentLove.setCreateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        commentLoveRepository.save(commentLove);

    }

}
