package com.example.demo.service;

import com.example.demo.controller.CommentForm;
import com.example.demo.domain.Comments;
import com.example.demo.domain.Posts;
import com.example.demo.domain.Users;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentRepository commentRepository;
    private final PostsService postsService;
    private final NoticeService noticeService;
    @Transactional
    public void addComment(Long id, CommentForm commentForm) {
        Comments comments = new Comments();
        Posts post = postsService.findOne(id);
        comments.setPost(post);
        comments.setUser(commentForm.getUser());
        comments.setComment(commentForm.getComment());
        comments.setRegisterTime(LocalDateTime.now());
        commentRepository.save(comments);
        noticeService.addCommentNotice(post.getId(), post.getUser(), commentForm.getUser(), comments.getRegisterTime());
    }

}
