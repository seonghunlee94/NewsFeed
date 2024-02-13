package org.example.prepurchase.global.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "newsfeed-service", url = "localhost:8083/api/v1/internal/newsfeed/feign")
public interface NewsFeedClient {

    @PostMapping("/save")
    public void saveNewsFeed(@RequestBody NewsFeedForm form);
}
