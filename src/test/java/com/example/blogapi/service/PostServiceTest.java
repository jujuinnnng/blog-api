package com.example.blogapi.service;

import com.example.blogapi.domain.Post;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("글 작성")
    void testPostCreate(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("글내용")
                .build();

        //when
        postService.write(postCreate);

        //then
        Assertions.assertEquals(1, postRepository.count());

        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목", post.getTitle());
        Assertions.assertEquals("글내용", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void testBoardInquiry() {
        //given
        Post requestPost = Post.builder()
                .title("ti")
                .content("con")
                .build();
        postRepository.save(requestPost);

        //when
        Post post = postService.board(requestPost.getId());

        Assertions.assertNotNull(post);
        Assertions.assertEquals("ti", post.getTitle());
        Assertions.assertEquals("con", post.getContent());

    }

}