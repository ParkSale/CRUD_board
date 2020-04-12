package com.example.demo.service;

import com.example.demo.controller.CommentForm;
import com.example.demo.domain.Comments;
import com.example.demo.domain.Users;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentRepository commentRepository;
    private final PostsService postsService;
    @Transactional
    public void addComment(Long id, CommentForm commentForm) {
        Comments comments = new Comments();
        comments.setPost(postsService.findOne(id));
        comments.setUser(commentForm.getUser());
        comments.setComment(commentForm.getComment());
        comments.setRegisterTime(LocalDateTime.now());
        commentRepository.save(comments);
    }
}
