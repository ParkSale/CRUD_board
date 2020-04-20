package com.example.demo.repository;

import com.example.demo.domain.Comments;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface CommentRepository extends CrudRepository<Comments, Long> {


}
