package com.example.demo.controller;

import com.example.demo.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostForm {
    private Long id;
    private String title;
    private String content;
    private Users user;
    private String fileName;

    public PostForm(Long id, String title,String content, Users user, String fileName){
        this.id=id;
        this.title=title;
        this.content = content;
        this.user=user;
        this.fileName = fileName;
    }
}
