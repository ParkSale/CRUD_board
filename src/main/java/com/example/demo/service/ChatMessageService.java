package com.example.demo.service;

import com.example.demo.controller.ChatMessageForm;
import com.example.demo.domain.chat.ChatMessage;
import com.example.demo.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final UsersService usersService;
    private final ChatRoomService chatRoomService;
    private final NoticeService noticeService;
    @Transactional
    public void save(ChatMessageForm message) {
        ChatMessage chatMessage = new ChatMessage(message.getMessage(),LocalDateTime.now(),chatRoomService.findById(message.getChatRoomId()).get()
        ,usersService.findByName(message.getSender()));
        chatMessageRepository.save(chatMessage);
        noticeService.addMessageNotice(chatMessage.getChatRoom(),chatMessage.getWriter(), message.getReceiver(),chatMessage.getTime());
    }
}
