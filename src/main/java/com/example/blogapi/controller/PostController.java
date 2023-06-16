package com.example.blogapi.controller;

import com.example.blogapi.domain.Post;
import com.example.blogapi.request.PostCreate;
import com.example.blogapi.response.PostResponse;
import com.example.blogapi.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request)  {
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse getBoard(@PathVariable Long postId){
        return postService.getBoard(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getBoardList(){
        return postService.getBoardList(1);
    }
}
