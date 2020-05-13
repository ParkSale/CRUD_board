package com.example.demo.repository;

import com.example.demo.domain.chat.ChatRoom;
import com.example.demo.domain.chat.ChatRoomJoin;
import com.example.demo.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRoomJoinRepository extends CrudRepository<ChatRoomJoin,Long> {
    public List<ChatRoomJoin> findByUser(Users user);

    public List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom);

}
