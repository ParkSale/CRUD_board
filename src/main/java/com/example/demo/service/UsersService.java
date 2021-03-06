package com.example.demo.service;

import com.example.demo.controller.UserForm;
import com.example.demo.domain.Users;
import com.example.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Transactional
    public String join(UserForm userForm) {
        if(usersRepository.findUsersByEmail(userForm.getEmail()) != null){
            return "email";
        }
        if(usersRepository.findUsersByName(userForm.getName()) != null){
            return "name";
        }
        Users user = new Users(userForm.getEmail(),userForm.getPassword(),userForm.getName());
        usersRepository.save(user);
        return "success";
    }

    @Transactional(readOnly = true)
    public Users findByName(String str) {
        return usersRepository.findUsersByName(str);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findUsersByEmail(username);
        return new User(user.getEmail(),user.getPassword(),authorities());
    }

    private Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Transactional(readOnly = true)
    public Users findByEmail(String email) {
        return usersRepository.findUsersByEmail(email);
    }
    @Transactional(readOnly = true)
    public List<String> findNameByContaining(String receiver) {
        return usersRepository.findNameByContaining(receiver);
    }
    @Transactional(readOnly = true)
    public Optional<Users> findById(Long id) {
        return usersRepository.findById(id);
    }
}
