package com.example.blogapi.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class PostSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer  size = 10;

    public long getOffest(){
        return (long) (Math.max(1, page) - 1) * Math.min(size,  MAX_SIZE);
    }
}
