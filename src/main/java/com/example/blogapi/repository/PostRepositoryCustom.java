package com.example.blogapi.repository;

import com.example.blogapi.domain.Post;
import com.example.blogapi.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
