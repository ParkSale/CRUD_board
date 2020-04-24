package com.example.demo.controller;

import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.Users;
import com.example.demo.service.ChatMessageService;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final UsersService usersService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;
    @MessageMapping("/chat/send")
    public void greeting(ChatMessage message) throws Exception {
        message.setSendTime(LocalDateTime.now());
        chatMessageService.send(message);
        String receiver = message.getReceiver();
        simpMessagingTemplate.convertAndSend("/topic/" + receiver,message);
    }

    @GetMapping("/chat")
    public String chatHome(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Users user = usersService.findByEmail(email);
        model.addAttribute("nickname",user.getName());
        List<ChatMessage> messages = chatMessageService.findByReceiver(user.getName());
        model.addAttribute("messages",messages);
        return "chat/main";
    }
}
