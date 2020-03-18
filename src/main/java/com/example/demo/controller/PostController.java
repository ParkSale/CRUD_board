package com.example.demo.controller;

import com.example.demo.domain.Posts;
import com.example.demo.domain.UserInfo;
import com.example.demo.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("postForm", postForm);
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

    @GetMapping("/posts/{postId}/read")
    public String readPost(@PathVariable("postId") Long postId, Model model){
        Posts post = postsService.findOne(postId);
        model.addAttribute("post",post);
        model.addAttribute("userName",userInfo.getUserName());
        return "board/read";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId, Model model){
        Posts post = postsService.findOne(postId);
        PostForm postForm = new PostForm();
        postForm.setId(post.getId());
        postForm.setAuthor(post.getAuthor());
        postForm.setContent(post.getContent());
        postForm.setTitle(post.getTitle());
        model.addAttribute("post",postForm);
        return "board/edit";
    }

    @PostMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId, PostForm postForm){
        Posts post = postsService.findOne(postId);
        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());
        postsService.save(post);
        return "redirect:/board/lists";
    }

    @GetMapping("/posts/{postId}/del")
    public String delPost(@PathVariable("postId") Long postId){
        postsService.delete(postId);
        return "redirect:/board/lists";
    }
}
