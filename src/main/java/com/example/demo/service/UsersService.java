package com.example.demo.service;

import com.example.demo.controller.UserForm;
import com.example.demo.domain.Users;
import com.example.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public String join(UserForm userForm) {
        if(usersRepository.findByEmail(userForm.getEmail()) != null){
            return "email";
        }
        if(usersRepository.findByName(userForm.getName()) != null){
            return "name";
        }
        Users user = new Users();
        user.setEmail(userForm.getEmail());
        user.setName(userForm.getName());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        usersRepository.save(user);
        return "success";
    }

    @Transactional(readOnly = true)
    public Users checkUser(UserForm userform) {
        Users user = usersRepository.findByEmail(userform.getEmail());
        if(user == null){
            return null;
        }
        else if (passwordEncoder.matches(userform.getPassword(), user.getPassword())){
            return user;
        }
        else return null;
    }
}
