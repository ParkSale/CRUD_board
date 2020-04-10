package com.example.demo.controller;

import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UsersService usersService;

    @GetMapping("/users/new")
    public String makeUserForm(Model model){
        model.addAttribute("userForm",new UserForm());
        model.addAttribute("email","");
        model.addAttribute("name","");
        return "user/new";
    }

    @PostMapping("/users/new")
    public String registerUser(UserForm userForm, Model model){
        String ret = usersService.join(userForm);
        model.addAttribute("userForm",userForm);
        model.addAttribute("email","");
        model.addAttribute("name","");
        if(ret.equals("email")){
            model.addAttribute("email","fail");
        }
        if(ret.equals("name")){
            model.addAttribute("name","fail");
        }
        if(ret.equals("success")) return "redirect:/home";
        else return "user/new";
    }
}