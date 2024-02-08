package org.example.prepurchase.domain.newsfeed.api;

import org.example.prepurchase.domain.newsfeed.application.NewsFeedService;
import org.example.prepurchase.domain.newsfeed.domain.NewsFeeds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/newsfeed")
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    @Autowired
    public NewsFeedController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @GetMapping
    public List<NewsFeeds> getNewsFees(@RequestParam String fromUser) {
        List<NewsFeeds> newsFeeds = newsFeedService.getNewsFeeds(fromUser);

        // 만약 결과가 null인 경우와 비어있는 경우를 처리할 필요가 있다면 여기서 처리 가능
        if (newsFeeds == null || newsFeeds.isEmpty()) {
            return null;
        }

        return newsFeeds;

    }

}
