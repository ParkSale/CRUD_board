package com.example.demo.service;

import com.example.demo.controller.UserForm;
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
    public String join(UserForm userForm) {
        Users user = new Users();
        if(usersRepository.findByEmail(userForm.getEmail()) != null){
            return "email";
        }
        if(usersRepository.findByName(userForm.getName()) != null){
            return "name";
        }
        user.setEmail(userForm.getEmail());
        user.setName(userForm.getName());
        user.setPassword(userForm.getPassword());
        usersRepository.save(user);
        return "success";
    }

    @Transactional
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
