package com.example.blogapi.controller;

import com.example.blogapi.domain.Post;
import com.example.blogapi.request.PostCreate;
import com.example.blogapi.request.PostEdit;
import com.example.blogapi.request.PostSearch;
import com.example.blogapi.response.PostResponse;
import com.example.blogapi.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public void post(@RequestBody @Valid PostCreate request)  {
        postService.write(request);
    }

    @GetMapping("/post/{postId}")
    public PostResponse getBoard(@PathVariable Long postId){
        return postService.getBoard(postId);
    }

    @GetMapping("/post")
    public List<PostResponse> getBoardList(@ModelAttribute PostSearch postSearch){
        return postService.getBoardList(postSearch);
    }

    @PatchMapping("/post/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request){
        return postService.edit(postId, request);
    }

    @DeleteMapping("/post/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }
}
