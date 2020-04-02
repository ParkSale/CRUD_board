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

    public Posts findOne(Long postId) {
        return em.find(Posts.class,postId);
    }

    public void delete(Long postId) {
        em.remove(em.find(Posts.class,postId));
    }
    public int getTotalCnt(){
        return em.createQuery("SELECT count(p) from Posts p",Integer.class).getSingleResult();
    }
}