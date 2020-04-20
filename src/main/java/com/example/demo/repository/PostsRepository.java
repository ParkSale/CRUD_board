package com.example.demo.repository;

import com.example.demo.domain.Posts;
import com.example.demo.domain.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostsRepository extends PagingAndSortingRepository<Posts,Long> {

    public List<Posts> findAll();
    public Posts findPostsById(Long id);
    public Page<Posts> findAll(Pageable paging);
    public Page<Posts> findByTitleContaining(String title, Pageable paging);
    public Page<Posts> findByContentContaining(String content, Pageable paging);
    public Page<Posts> findByUser(Users users, Pageable pageable);
}