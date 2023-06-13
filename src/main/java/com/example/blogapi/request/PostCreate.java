package com.example.blogapi.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
//@AllArgsConstructor
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "텍스트를 입력해주세요.")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
