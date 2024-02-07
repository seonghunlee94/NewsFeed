package org.example.prepurchase.domain.newsfeed.application;

import org.example.prepurchase.domain.newsfeed.dao.NewsFeedRepository;
import org.example.prepurchase.domain.newsfeed.domain.NewsFeeds;
import org.example.prepurchase.global.config.NewsFeedType;
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

    public void createNewsFeed(String title, String userId, String username, NewsFeedType newsFeedType) {

        NewsFeeds newsFeed = new NewsFeeds();

        newsFeed.setSenderId(username);
        newsFeed.setReceiverId(userId);
        newsFeed.setServiceType(String.valueOf(newsFeedType));
        newsFeed.setPostName(title);
        newsFeed.setCreateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        newsFeedRepository.save(newsFeed);
    }

    public List<NewsFeeds> getNewsFeeds(String fromUser) {

        return newsFeedRepository.getNewsFeeds(fromUser);
    }


}
