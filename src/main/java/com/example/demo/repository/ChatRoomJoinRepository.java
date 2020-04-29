package com.example.demo.repository;

import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.ChatRoomJoin;
import com.example.demo.domain.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomJoinRepository extends CrudRepository<ChatRoomJoin,Long> {
    public List<ChatRoomJoin> findByUser(Users user);

    public List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom);

}
