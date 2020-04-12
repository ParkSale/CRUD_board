package com.example.demo.service;

import com.example.demo.controller.CommentForm;
import com.example.demo.domain.Comments;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostsRepository;
import jdk.vm.ci.meta.Local;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentRepository commentRepository;
    private final PostsService postsService;
    @Transactional
    public void addComment(Long id, CommentForm commentForm) {
        Comments comments = new Comments();
        comments.setPost(postsService.findOne(id));
        comments.setAuthor(commentForm.getAuthor());
        comments.setComment(commentForm.getComment());
        comments.setRegisterTime(LocalDateTime.now());
        commentRepository.save(comments);
    }
}
