package com.example.blogapi.service;

import com.example.blogapi.domain.Post;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.request.PostCreate;
import com.example.blogapi.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

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
                .title("123456789012345")
                .content("con")
                .build();
        postRepository.save(requestPost);

        //when
        PostResponse postResponse = postService.board(requestPost.getId());

        Assertions.assertNotNull(postResponse);
        Assertions.assertEquals("123456789012345", postResponse.getTitle());
        Assertions.assertEquals("con", postResponse.getContent());
    }
}