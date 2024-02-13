package org.example.prepurchase.domain.post.dao;

import org.example.prepurchase.domain.post.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {

    Optional<Posts> findById(Long id);
}
