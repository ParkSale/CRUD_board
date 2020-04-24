package com.example.demo.service;

import com.example.demo.domain.ChatMessage;
import com.example.demo.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    public void send(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    public List<ChatMessage> findByReceiver(String name) {
        return chatMessageRepository.findByReceiver(name);
    }
}
