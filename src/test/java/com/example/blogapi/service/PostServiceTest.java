package com.example.blogapi.service;

import com.example.blogapi.domain.Post;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.request.PostCreate;
import com.example.blogapi.request.PostSearch;
import com.example.blogapi.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        assertEquals(1, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("글내용", post.getContent());
    }

    @Test
    @DisplayName("글 단건 조회")
    void testBoardInquiry() {
        //given
        Post requestPost = Post.builder()
                .title("1234567890")
                .content("con")
                .build();
        postRepository.save(requestPost);

        //when
        PostResponse postResponse = postService.getBoard(requestPost.getId());

        Assertions.assertNotNull(postResponse);
        assertEquals("1234567890", postResponse.getTitle());
        assertEquals("con", postResponse.getContent());
    }

    @Test
    @DisplayName("페이지 조회")
    void testBoardPaging() {
        //given
        List<Post> requestPosts = IntStream.range(0,20)
                        .mapToObj(i -> Post.builder()
                                .title("제목"+i)
                                .content("내용"+i)
                                .build())
                        .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        //when
        List<PostResponse> posts = postService.getBoardList(postSearch);

        //then
        assertEquals(10L, posts.size());
        assertEquals("제목19", posts.get(0).getTitle()); //서비스에 내림차순으로 설정
    }
}