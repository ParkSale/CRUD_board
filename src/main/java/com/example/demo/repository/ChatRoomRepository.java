package com.example.demo.repository;

import com.example.demo.domain.ChatRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChatRoomRepository extends CrudRepository<ChatRoom,Long> {
    public Optional<ChatRoom> findById(Long id);
}
