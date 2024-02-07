package org.example.prepurchase.domain.newsfeed.dao;

import org.example.prepurchase.domain.newsfeed.domain.NewsFeeds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsFeedRepository extends JpaRepository<NewsFeeds, Long>, NewsFeedQRepository {

}
