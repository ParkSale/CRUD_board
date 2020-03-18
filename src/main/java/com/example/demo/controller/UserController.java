package com.example.demo.controller;

import com.example.demo.domain.UserInfo;
import com.example.demo.domain.Users;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserInfo userInfo;
    private final UsersService usersService;

    @GetMapping("/users/new")
    public String makeUserForm(Model model){
        model.addAttribute("form",new UserForm());
        return "user/new";
    }

    @PostMapping("/users/new")
    public String newUser(UserForm form){
        Users user = new Users();
        user.setEmail(form.getEmail());
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        usersService.join(user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(){
        userInfo.setUserName("");
        userInfo.setUserEmail("");
        return "redirect:/";
    }
}