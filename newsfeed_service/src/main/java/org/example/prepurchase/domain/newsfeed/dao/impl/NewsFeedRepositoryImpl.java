package org.example.prepurchase.domain.newsfeed.dao.impl;

import static org.example.prepurchase.domain.newsfeed.domain.QNewsFeeds.newsFeeds;
import static org.example.prepurchase.domain.follow.domain.QFollows.follows;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.prepurchase.domain.newsfeed.dao.NewsFeedQRepository;
import org.example.prepurchase.domain.newsfeed.domain.NewsFeeds;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class NewsFeedRepositoryImpl implements NewsFeedQRepository {

    private final JPAQueryFactory queryFactory;

    public List<NewsFeeds> getNewsFeeds(String fromUser) {
        List<NewsFeeds> results = new ArrayList<>();

        List<String> followedUsers = queryFactory
                .select(follows.toUser)
                .from(follows)
                .where(follows.fromUser.eq(fromUser))
                .fetch();

        List<NewsFeeds> newsFeedsFromUser = queryFactory
                .select(newsFeeds)
                .from(newsFeeds)
                .where(newsFeeds.receiverId.eq(fromUser))
                .fetch();

        results.addAll(newsFeedsFromUser);

        List<NewsFeeds> newsFeedsForFollowedUsers = queryFactory
                .select(newsFeeds)
                .from(newsFeeds)
                .where(newsFeeds.receiverId.in(followedUsers))
                .fetch();

        // 결과 리스트에 추가
        results.addAll(newsFeedsForFollowedUsers);

        results.sort(Comparator.comparing(NewsFeeds::getCreateTime).reversed());

        return results;
    }
}
