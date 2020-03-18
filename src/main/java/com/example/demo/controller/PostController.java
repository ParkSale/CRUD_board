package com.example.demo.controller;

import com.example.demo.domain.Posts;
import com.example.demo.domain.UserInfo;
import com.example.demo.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final UserInfo userInfo;
    private final PostsService postsService;

    @GetMapping("/board/lists")
    public String showBoard(Model model){
        List<Posts> board =postsService.findAll();
        model.addAttribute("posts",board);
        return "board/lists";
    }

    @GetMapping("/board/newPost")
    public String newPost(Model model){
        PostForm postForm = new PostForm();
        postForm.setAuthor(userInfo.getUserName());
        model.addAttribute("boardForm", postForm);
        return "board/newPost";
    }
    @PostMapping("/posts/new")
    public String registerPost(PostForm postForm){
        Posts post = new Posts();
        post.setTitle(postForm.getTitle());
        post.setAuthor(userInfo.getUserName());
        post.setContent(postForm.getContent());
        postsService.save(post);
        return "redirect:/board/lists";
    }

}
