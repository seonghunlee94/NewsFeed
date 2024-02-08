package org.example.prepurchase.domain.follow.application;

import org.example.prepurchase.domain.follow.dao.FollowRepository;
import org.example.prepurchase.domain.follow.domain.Follows;
import org.example.prepurchase.domain.follow.dto.FollowDto;
import org.example.prepurchase.domain.newsfeed.application.NewsFeedService;
import org.example.prepurchase.global.config.NewsFeedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final NewsFeedService newsFeedService;


    @Autowired
    public FollowService(FollowRepository followRepository,NewsFeedService newsFeedService) {
        this.followRepository = followRepository;
        this.newsFeedService = newsFeedService;
    }

    public void follow(String username, FollowDto followUser) {


        String toUser = followUser.getToUser();
        // 추후에 (다른 모듈에서) Users 엔터티 중 username을 조회해서 팔로우 하려고 하는 user가 있는지 확인하기.

        if (username.equals(toUser)) {
            throw new IllegalArgumentException("자신을 팔로우할 수 없습니다.");
        }

        Optional<Follows> checkFollow = followRepository.findFollowsBy(toUser, username);

        if (!checkFollow.isEmpty()) {
            throw new IllegalArgumentException("이미 팔로우한 유저입니다.");
        }

        Follows newFollow = new Follows();



        newFollow.setFromUser(username);
        newFollow.setToUser(toUser);
        newFollow.setCreateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));


        followRepository.save(newFollow);

        newsFeedService.createNewsFeed("", toUser, username, NewsFeedType.FOLLOW);

    }

}
