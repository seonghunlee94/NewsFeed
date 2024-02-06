package org.example.prepurchase.domain.post.application;

import org.example.prepurchase.domain.post.dao.PostRepository;
import org.example.prepurchase.domain.post.domain.Posts;
import org.example.prepurchase.domain.post.dto.CreatePostDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class PostService {

    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void createPost(String username, CreatePostDto createPost) {

        Posts post = new Posts();
        post.setUserId(username);
        post.setTitle(createPost.getTitle());
        post.setContent(createPost.getContent());
        post.setCreateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        post.setUpdateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        postRepository.save(post);
    }



}
