package org.example.prepurchase.domain.comment.dao;

import org.example.prepurchase.domain.comment.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {

}
