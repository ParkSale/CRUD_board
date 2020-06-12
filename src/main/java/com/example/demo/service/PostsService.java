package com.example.demo.service;
import com.example.demo.domain.Pagination;
import com.example.demo.domain.Posts;
import com.example.demo.domain.Users;
import com.example.demo.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public void save(Posts post) {
        postsRepository.save(post);
    }
    @Transactional(readOnly = true)
    public Posts findOne(Long postId) {
        return postsRepository.findPostsById(postId);
    }

    @Transactional
    public void delete(Long postId) {
        postsRepository.delete(findOne(postId));
    }


    public Pagination setPagination(long page, long totalCnt) {
        Pagination pagination = new Pagination();
        pagination.pageInfo(page, totalCnt);
        return pagination;
    }

    @Transactional
    public void readPost(Posts post) {
        post.setViewCnt(post.getViewCnt() + 1);
    }
    @Transactional(readOnly = true)
    public Page<Posts> getPage(PageRequest pageRequest) {
        return postsRepository.findAll(pageRequest);
    }
    @Transactional(readOnly = true)
    public Page<Posts> getPageByTitle(String str, PageRequest pageRequest) {
        return postsRepository.findByTitleContaining(str,pageRequest);
    }
    @Transactional(readOnly = true)
    public Page<Posts> getPageByContent(String str, PageRequest pageRequest) {
        return postsRepository.findByContentContaining(str,pageRequest);
    }
    @Transactional(readOnly = true)
    public Page<Posts> getPageByUsers(Users user, PageRequest pageRequest) {
        return postsRepository.findByUser(user,pageRequest);
    }

    public List<Posts> titleSetting(List<Posts> content) {
        List<Posts> ret = new ArrayList<>();
        for(Posts posts : content){
            if(posts.getTitle().length() >= 15){
                posts.setTitle(posts.getTitle().substring(0,14) + "...");
            }
            if(posts.getComments().size() != 0){
                posts.setTitle(posts.getTitle() + "[" + posts.getComments().size() + "]");
            }
            ret.add(posts);
        }
        return ret;
    }
}

