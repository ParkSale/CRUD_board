package com.example.demo.repository;

import com.example.demo.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface UsersRepository extends CrudRepository<Users,Long> {
    public Users findUsersByEmail(String email);
    public Users findUsersByName(String name);
}
