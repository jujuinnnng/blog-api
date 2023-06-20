package com.example.blogapi.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Builder
public class PostEdit {
    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "텍스트를 입력해주세요.")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
