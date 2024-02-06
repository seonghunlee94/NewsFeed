package org.example.prepurchase.domain.comment.dao;

import org.example.prepurchase.domain.comment.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {

    @Query("SELECT c FROM Comments c WHERE c.post.id = :postId AND c.UserId = :userId")
    Optional<Comments> findCommentsByPostAndUserId(Long postId, String userId);


}
