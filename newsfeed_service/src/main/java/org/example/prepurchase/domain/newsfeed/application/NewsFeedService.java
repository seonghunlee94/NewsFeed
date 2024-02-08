package org.example.prepurchase.domain.newsfeed.application;

import org.example.prepurchase.domain.newsfeed.dao.NewsFeedRepository;
import org.example.prepurchase.domain.newsfeed.domain.NewsFeeds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;


    @Autowired
    public NewsFeedService(NewsFeedRepository newsFeedRepository) {
        this.newsFeedRepository = newsFeedRepository;
    }

    public void createNewsFeed(NewsFeeds newsFeed) {

        newsFeed.setCreateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        newsFeedRepository.save(newsFeed);
    }

    public List<NewsFeeds> getNewsFeeds(String fromUser) {

        return newsFeedRepository.getNewsFeeds(fromUser);
    }


}
