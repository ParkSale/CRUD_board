package com.example.demo.controller;

import com.example.demo.config.LoginUser;
import com.example.demo.config.SessionUser;
import com.example.demo.domain.Users;
import com.example.demo.service.CommentsService;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentsService commentsService;
    private final UsersService usersService;
    @PostMapping("/comments/new/{id}")
    public String newComment(@PathVariable("id") Long id, CommentForm commentForm, @LoginUser SessionUser sessionUser){
        if(sessionUser == null){
            return "redirect:/board/lists/1";
        }
        Users user = usersService.findByName(sessionUser.getName());
        commentForm.setUser(user);
        commentsService.addComment(id,commentForm);
        String ret = "redirect:/posts/read/" + id;
        return ret;
    }
}
