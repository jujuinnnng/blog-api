package com.example.blogapi.controller;

import com.example.blogapi.domain.Post;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureMockMvc
@SpringBootTest

class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/제목 글내용 데이터 확인")
    void testPostCreate() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목")
                .content("글내용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        System.out.println(json);
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 title값은 필수다.")
    void testNoTitle() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .content("글내용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장된다.")
    void testBoardSave() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목")
                .content("글내용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        //then
        Assertions.assertEquals(1, postRepository.count());

        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목", post.getTitle());
        Assertions.assertEquals("글내용", post.getContent());
    }

    @Test
    @DisplayName("글 단건 조회 및 타이틀 값 10자 제한 기능 test")
    void testBoardInquiry() throws Exception {
        //given
        Post requestPost = Post.builder()
                .title("123456789012345")
                .content("con")
                .build();
        postRepository.save(requestPost);

        //expected(when+then)
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", requestPost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(requestPost.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("con"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 다건 조회")
    void testBoardListInquiry() throws Exception {
        //given
        Post requestPost1 = postRepository.save(Post.builder()
                .title("ti1")
                .content("con1")
                .build());

        Post requestPost2 = postRepository.save(Post.builder()
                .title("ti2")
                .content("con2")
                .build());

        //expected(when+then)
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(requestPost1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("ti1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("con1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(requestPost2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("ti2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("con2"))
                .andDo(MockMvcResultHandlers.print());
    }
}