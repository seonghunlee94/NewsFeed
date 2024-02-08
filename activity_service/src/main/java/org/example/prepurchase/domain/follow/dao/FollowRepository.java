package org.example.prepurchase.domain.follow.dao;

import org.example.prepurchase.domain.follow.domain.Follows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follows, Long> {

    @Query("SELECT f FROM Follows f WHERE f.toUser = :toUser AND f.fromUser = :fromUser")
    Optional<Follows> findFollowsBy(String toUser, String fromUser);

}