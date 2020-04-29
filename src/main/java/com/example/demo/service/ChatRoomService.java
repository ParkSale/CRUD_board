package com.example.demo.service;

import com.example.demo.domain.ChatRoom;
import com.example.demo.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    public Optional<ChatRoom> findById(Long id) {
        return chatRoomRepository.findById(id);
    }

}
