package com.example.demo.controller;

import com.example.demo.domain.Users;
import com.example.demo.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentsService commentsService;
    @PostMapping("/comments/{id}/new")
    public String newComment(@PathVariable("id") Long id, CommentForm commentForm, HttpServletRequest request){
        Users user = (Users) request.getSession().getAttribute("user");
        commentForm.setUser(user);
        commentsService.addComment(id,commentForm);
        String ret = "redirect:/posts/" + id + "/read";
        return ret;
    }
}
