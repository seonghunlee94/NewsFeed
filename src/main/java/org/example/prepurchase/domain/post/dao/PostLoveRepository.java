package org.example.prepurchase.domain.post.dao;

import org.example.prepurchase.domain.post.domain.PostLove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLoveRepository extends JpaRepository<PostLove, Long> {

    @Query("SELECT p FROM PostLove p WHERE p.post.id = :postId AND p.UserId = :userId")
    Optional<PostLove> findPostLoveByPostAndUserId(Long postId, String userId);

}

