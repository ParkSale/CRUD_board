package com.example.demo.controller;

import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.ChatRoomJoin;
import com.example.demo.domain.Users;
import com.example.demo.repository.ChatRoomRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.ChatMessageService;
import com.example.demo.service.ChatRoomJoinService;
import com.example.demo.service.ChatRoomService;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final UsersService usersService;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatRoomService chatRoomService;
    @GetMapping("/chat")
    public String chatHome(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Users user = usersService.findByEmail(email);
        model.addAttribute("nickname",user.getName());
        List<ChatRoomJoin> chatRoomJoins = chatRoomJoinService.findByUser(user);
        List<ChatRoomForm> chatRooms = new ArrayList<>();
        for(ChatRoomJoin tmp : chatRoomJoins){
            ChatRoomForm chatRoomForm = new ChatRoomForm();
            ChatRoom chatRoom = tmp.getChatRoom();
            chatRoomForm.setId(chatRoom.getId());
            if(chatRoom.getMessages().size() != 0) {
                ChatMessage lastMessage = chatRoom.getMessages().get(chatRoom.getMessages().size() - 1);
                chatRoomForm.setLastMessage(lastMessage.getMessage());
                chatRoomForm.setWriter(lastMessage.getWriter().getName());
                chatRoomForm.setTime(lastMessage.getTime());
                chatRooms.add(chatRoomForm);
            }
            else{
                chatRoomJoinService.delete(tmp);
            }
        }
        model.addAttribute("chatRooms",chatRooms);
        return "chat/main";
    }

    @PostMapping("/chat/newChat")
    public String newChat(@RequestParam("receiver") String user1, @RequestParam("sender") String user2){
        Long chatRoomId = chatRoomJoinService.newRoom(user1,user2);
        return "redirect:/personalChat/" + chatRoomId;
    }

    @RequestMapping("/personalChat/{chatRoomId}")
    public String goChat(@PathVariable("chatRoomId") Long chatRoomId,Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Users user = usersService.findByEmail(email);
        Optional<ChatRoom> opt = chatRoomService.findById(chatRoomId);
        ChatRoom chatRoom = opt.get();
        List<ChatMessage> messages = chatRoom.getMessages();
        Collections.reverse(messages);
        List<ChatRoomJoin> list = chatRoomJoinService.findByChatRoom(chatRoom);
        model.addAttribute( "messages",messages);
        model.addAttribute("nickname",user.getName());
        model.addAttribute("chatRoomId",chatRoomId);
        int cnt = 0;
        for(ChatRoomJoin join : list){
            if(join.getUser().getName().equals(user.getName()) == false){
                model.addAttribute("receiver",join.getUser().getName());
                ++cnt;
            }
        }
        if(cnt >= 2){
            return "redirect:/chat";
        }
        return "chat/chatRoom";
    }
}
