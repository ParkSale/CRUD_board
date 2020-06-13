package com.example.demo.service;

import com.example.demo.controller.CommentForm;
import com.example.demo.domain.Comments;
import com.example.demo.domain.Posts;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentRepository commentRepository;
    private final PostsService postsService;
    private final NoticeService noticeService;
    @Transactional
    public void addComment(Long id, CommentForm commentForm) {
        Posts post = postsService.findOne(id);
        Comments comments = new Comments(post,commentForm.getUser(),commentForm.getComment(),LocalDateTime.now());
        commentRepository.save(comments);
        noticeService.addCommentNotice(post.getId(), post.getUser(), commentForm.getUser(), comments.getRegisterTime());
    }

}
