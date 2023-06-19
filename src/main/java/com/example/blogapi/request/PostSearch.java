package com.example.blogapi.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PostSearch {
    private int page;
    private int size;

    @Builder
    public PostSearch(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
