package org.example.prepurchase.domain.post.application;

import org.example.prepurchase.domain.post.dao.PostLoveRepository;
import org.example.prepurchase.domain.post.dao.PostRepository;
import org.example.prepurchase.domain.post.domain.PostLove;
import org.example.prepurchase.domain.post.domain.Posts;
import org.example.prepurchase.domain.post.dto.CreatePostDto;
import org.example.prepurchase.global.client.NewsFeedClient;
import org.example.prepurchase.global.client.NewsFeedForm;
import org.example.prepurchase.global.config.NewsFeedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostLoveRepository postLoveRepository;

    private final NewsFeedClient newsFeedClient;


    @Autowired
    public PostService(PostRepository postRepository, PostLoveRepository postLoveRepository, NewsFeedClient newsFeedClient) {
        this.postRepository = postRepository;
        this.postLoveRepository = postLoveRepository;
        this.newsFeedClient = newsFeedClient;
    }

    public void createPost(String username, CreatePostDto createPost) {

        // 추후 포스트 수정 사항 고려.
        Posts post = new Posts();
        post.setUserId(username);
        post.setTitle(createPost.getTitle());
        post.setContent(createPost.getContent());
        post.setCreateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        post.setUpdateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        postRepository.save(post);
    }


    public void lovePost(String idString, String title, String userId, String username) {

        Long id = Long.valueOf(idString);


        Optional<Posts> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            throw new IllegalArgumentException("주어진 id에 해당하는 포스트를 찾을 수 없습니다.");
        }

        Posts post = postOptional.get();

        Optional<PostLove> checkLove = postLoveRepository.findPostLoveByPostAndUserId(post.getId(), username);
        if (!checkLove.isEmpty()) {
            throw new IllegalArgumentException("이미 '좋아요' 한 게시물입니다.");
        }


        PostLove postLove = new PostLove();

        postLove.setPost(post);
        postLove.setUserId(username);
        postLove.setCreateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        postLoveRepository.save(postLove);

        // 추후에 모듈화 작업할 때, 호출 방식 바꾸기.
        if (!userId.equals(username)) {
            NewsFeedForm newsFeedForm = new NewsFeedForm();

            newsFeedClient.saveNewsFeed(newsFeedForm.builder()
                    .senderId(username)
                    .receiverId(userId)
                    .serviceType(NewsFeedType.POST_LOVE)
                    .postName(title).build());
        }

    }

}
