package com.example.demo.controller;


import com.example.demo.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class CommentForm {
    private Users user;
    private String comment;
}
