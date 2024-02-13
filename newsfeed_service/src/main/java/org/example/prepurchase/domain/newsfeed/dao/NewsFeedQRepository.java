package org.example.prepurchase.domain.newsfeed.dao;

import org.example.prepurchase.domain.newsfeed.domain.NewsFeeds;

import java.util.List;

public interface NewsFeedQRepository {
    List<NewsFeeds> getNewsFeeds(String receiverId);
}
