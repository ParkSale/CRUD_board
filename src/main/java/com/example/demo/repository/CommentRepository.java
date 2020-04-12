package com.example.demo.repository;

import com.example.demo.domain.Comments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void save(Comments comments) {
        em.persist(comments);
    }

}
