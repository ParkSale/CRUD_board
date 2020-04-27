package com.example.demo.repository;

import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.ChatRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ChatMessageRepository extends CrudRepository<ChatMessage,Long> {

}
