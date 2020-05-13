package com.example.demo.repository;

import com.example.demo.domain.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends CrudRepository<Users,Long> {
    public Users findUsersByEmail(String email);
    public Users findUsersByName(String name);
    @Query("SELECT u.name FROM Users u WHERE u.name LIKE :name")
    public List<String> findNameByContaining(@Param("name")String receiver);
}
