package com.example.demo.repository;

import com.example.demo.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UsersRepository {
    private final EntityManager em;

    public void save(Users user){
        em.persist(user);
    }

    public Users findByEmail(String email){
        return (Users) em.createQuery("SELECT u from Users u WHERE u.email = :email",Users.class)
                .setParameter("email",email).getSingleResult();
    }
}
