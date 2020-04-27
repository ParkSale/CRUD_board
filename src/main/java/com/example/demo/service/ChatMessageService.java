package com.example.demo.service;

import com.example.demo.controller.ChatMessageForm;
import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.Users;
import com.example.demo.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final UsersService usersService;
    private final ChatRoomService chatRoomService;

    public void save(ChatMessageForm message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message.getMessage());
        chatMessage.setTime(LocalDateTime.now());
        chatMessage.setWriter(usersService.findByName(message.getSender()));
        chatMessage.setChatRoom(chatRoomService.findById(message.getChatRoomId()).get());
        chatMessageRepository.save(chatMessage);
    }
}
