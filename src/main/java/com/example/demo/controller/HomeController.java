package com.example.demo.controller;

import com.example.demo.domain.UserInfo;
import com.example.demo.domain.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserInfo userInfo;
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/")
    public String home(Model model){
        if(userInfo.getUserName() != ""){
            return "redirect:board/lists/1";
        }
        model.addAttribute("userForm",new UserForm());
        model.addAttribute("state","");
        return "home";
    }

    @PostMapping("/login")
    public String loginCheck(UserForm userform,Model model){
        Users user = usersService.checkUser(userform);
        if(user == null){
            model.addAttribute("state","fail");
            return "home";
        }
        else {
            userInfo.setUserEmail(user.getEmail());
            userInfo.setUserName(user.getName());
            return "redirect:/board/lists/1";
        }
    }
}
