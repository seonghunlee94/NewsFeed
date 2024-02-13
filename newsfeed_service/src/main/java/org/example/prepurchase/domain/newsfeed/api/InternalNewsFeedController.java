package org.example.prepurchase.domain.newsfeed.api;


import org.example.prepurchase.domain.newsfeed.application.NewsFeedService;
import org.example.prepurchase.domain.newsfeed.domain.NewsFeeds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal/newsfeed")
public class InternalNewsFeedController {

    private final NewsFeedService newsFeedService;

    @Autowired
    public InternalNewsFeedController(NewsFeedService newsFeedService) {
        this.newsFeedService = newsFeedService;
    }

    @PostMapping("/feign/save")
    public ResponseEntity<Void> saveNewsFeed(@RequestBody NewsFeeds newsFeed) {
        newsFeedService.createNewsFeed(newsFeed);

        return ResponseEntity.ok().build();
    }

}
