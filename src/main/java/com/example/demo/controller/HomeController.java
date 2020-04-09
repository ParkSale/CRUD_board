package com.example.demo.controller;

import com.example.demo.domain.Users;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UsersService usersService;
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Users user= (Users) session.getAttribute("user");
        if(user != null){
            return "redirect:board/lists/1";
        }
        model.addAttribute("userForm",new UserForm());
        model.addAttribute("state","");
        return "home";
    }

    @PostMapping("/login")
    public String loginCheck(UserForm userform, Model model, HttpServletRequest request){
        Users user = usersService.checkUser(userform);
        if(user == null){
            model.addAttribute("state","fail");
            return "home";
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(60*30);
            return "redirect:/board/lists/1";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }
}
