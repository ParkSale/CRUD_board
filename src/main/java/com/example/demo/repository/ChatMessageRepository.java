package com.example.demo.repository;

import com.example.demo.domain.chat.ChatMessage;
import org.springframework.data.repository.CrudRepository;


public interface ChatMessageRepository extends CrudRepository<ChatMessage,Long> {

}
