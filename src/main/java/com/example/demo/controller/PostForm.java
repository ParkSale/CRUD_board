package com.example.demo.controller;

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
    private String author;
    private String realFileName;
    private String fileName;
}
