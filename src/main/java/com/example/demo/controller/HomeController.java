package com.example.demo.controller;

import com.example.demo.domain.Users;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UsersService usersService;
    @GetMapping("/")
    public String board(){
        return "redirect:/board/lists/1";
    }
    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Users user = usersService.findByEmail(email);
        if(user != null){
            return "redirect:/board/lists/1";
        }
        UserForm userForm = new UserForm();
        userForm.setEmail((String) request.getAttribute("email"));
        String str = request.getParameter("error");
        model.addAttribute("userName","");
        if(str == null){
            model.addAttribute("userForm",userForm);
            model.addAttribute("state","");
        }
        else{
            model.addAttribute("userForm",userForm);
            model.addAttribute("state","fail");
        }
        return "home";
    }
}
