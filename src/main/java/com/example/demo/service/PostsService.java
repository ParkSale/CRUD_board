package com.example.demo.service;

import com.example.demo.domain.Posts;
import com.example.demo.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional(readOnly = true)
    public List<Posts> findAll() {
        return postsRepository.findAll();
    }

    @Transactional
    public void save(Posts post) {
        postsRepository.save(post);
    }
}

