package com.example.demo.controller;

import com.example.demo.domain.Users;
import com.example.demo.service.CommentsService;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
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
    public String newComment(@PathVariable("id") Long id, CommentForm commentForm, HttpServletRequest request){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Users user = usersService.findByEmail(email);
        if(user == null){
            return "redirect:/board/lists/1";
        }
        commentForm.setUser(user);
        commentsService.addComment(id,commentForm);
        String ret = "redirect:/posts/read/" + id;
        return ret;
    }
}
