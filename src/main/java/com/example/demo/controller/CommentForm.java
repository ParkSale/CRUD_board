package com.example.demo.controller;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class CommentForm {
    private String author;
    private String comment;
}
