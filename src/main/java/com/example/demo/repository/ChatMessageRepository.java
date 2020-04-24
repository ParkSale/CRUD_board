package com.example.demo.repository;

import com.example.demo.domain.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatMessageRepository extends CrudRepository<ChatMessage,Long> {
    List<ChatMessage> findByReceiver(String name);
}
