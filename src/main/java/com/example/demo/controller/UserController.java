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
        model.addAttribute("userForm",new UserForm());
        model.addAttribute("email","");
        model.addAttribute("name","");
        return "user/new";
    }

    @PostMapping("/users/new")
    public String registerUser(UserForm userForm, Model model){
        String ret = usersService.join(userForm);
        model.addAttribute("userForm",userForm);
        if(ret.equals("email")){
            model.addAttribute("email","fail");
        }
        if(ret.equals("name")){
            model.addAttribute("name","fail");
        }
        if(ret.equals("success")) return "redirect:/";
        else return "user/new";
    }

    @GetMapping("/logout")
    public String logout(){
        userInfo.setUserName("");
        userInfo.setUserEmail("");
        return "redirect:/";
    }
}