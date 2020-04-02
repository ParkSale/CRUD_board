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

    public List<Posts> findByTitle(String str) {
        return em.createQuery("SELECT p from Posts p WHERE p.title = :title ORDER BY p.id DESC", Posts.class).setParameter("title",str)
                .getResultList();
    }
    public List<Posts> findByContent(String str){
        str = "%"+str + "%";
        return em.createQuery("SELECT p FROM Posts p WHERE p.content LIKE :content ORDER BY p.id DESC",Posts.class).setParameter("content",str)
                .getResultList();
    }
    public List<Posts> findByAuthor(String str){
        return em.createQuery("SELECT p from Posts p WHERE p.author = :author ORDER BY p.id DESC", Posts.class).setParameter("author",str)
                .getResultList();
    }
}