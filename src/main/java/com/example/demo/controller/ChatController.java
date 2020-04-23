package com.example.demo.controller;

import com.example.demo.domain.Users;
import com.example.demo.service.ChatService;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UsersService usersService;
    private final ChatService chatService;
    @GetMapping("/chat")
    public String chatHome(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Users user = usersService.findByEmail(email);
        model.addAttribute("nickname",user.getName());
        model.addAttribute("participants",chatService.getParticipants());
        return "chat/main";
    }
}
