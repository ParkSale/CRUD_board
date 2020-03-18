package com.example.demo.service;

import com.example.demo.domain.Users;
import com.example.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public void join(Users user) {
        usersRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
