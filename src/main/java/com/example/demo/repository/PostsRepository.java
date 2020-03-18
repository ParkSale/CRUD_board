package com.example.demo.repository;

import com.example.demo.domain.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostsRepository {
    private final EntityManager em;

    public List<Posts> findAll(){
        return em.createQuery("SELECT p from Posts p ORDER BY p.id DESC",Posts.class).getResultList();
    }

    public void save(Posts post) {
        em.persist(post);
    }
}