package org.example.prepurchase.domain.comment.dao;

import org.example.prepurchase.domain.comment.domain.CommentLove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLoveRepository extends JpaRepository<CommentLove, Long> {

    @Query("SELECT c FROM CommentLove c WHERE c.comment.id = :commentId AND c.userId = :userId")
    Optional<CommentLove> findCommentLoveByCommentrAndUserId(Long commentId, String userId);


}
