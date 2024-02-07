package org.example.prepurchase.domain.post.application;

import org.example.prepurchase.domain.post.dao.PostLoveRepository;
import org.example.prepurchase.domain.post.dao.PostRepository;
import org.example.prepurchase.domain.post.domain.PostLove;
import org.example.prepurchase.domain.post.domain.Posts;
import org.example.prepurchase.domain.post.dto.CreatePostDto;
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


    public PostService(PostRepository postRepository, PostLoveRepository postLoveRepository) {
        this.postRepository = postRepository;
        this.postLoveRepository = postLoveRepository;
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


    }

}
