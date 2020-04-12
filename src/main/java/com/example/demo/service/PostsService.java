package com.example.demo.service;
import com.example.demo.domain.Pagination;
import com.example.demo.domain.Posts;
import com.example.demo.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
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

    @Transactional(readOnly = true)
    public Posts findOne(Long postId) {
        return postsRepository.findOne(postId);
    }

    @Transactional
    public void delete(Long postId) {
        postsRepository.delete(postId);
    }

    @Transactional(readOnly = true)
    public List<Posts> findByTitle(String str) {
        return postsRepository.findByTitle(str);
    }

    @Transactional(readOnly = true)
    public List<Posts> findByContent(String str) {
        return postsRepository.findByContent(str);
    }
    public List<Posts> getBoard(List<Posts> boardAll, int size, int page, int  totalCnt){
        List<Posts> board = new ArrayList<>();
        for(int i = (page - 1)*size; i < Math.min(totalCnt,(page - 1)*size + size);++i){
            board.add(boardAll.get(i));
        }
        return board;
    }

    public Pagination setPagination(int page, int totalCnt) {
        Pagination pagination = new Pagination();
        pagination.pageInfo(page, totalCnt);
        return pagination;
    }

    @Transactional
    public void readPost(Posts post) {
        post.setViewCnt(post.getViewCnt() + 1);
    }

}

