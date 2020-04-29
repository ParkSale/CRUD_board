package com.example.demo.repository;

import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface UsersRepository extends CrudRepository<Users,Long> {
    public Users findUsersByEmail(String email);
    public Users findUsersByName(String name);
    @Query("SELECT u.name FROM Users u WHERE u.name LIKE :name")
    public List<String> findNameByContaining(@Param("name")String receiver);
}
