package com.example.demo.service;

import com.example.demo.domain.ChatRoom;
import com.example.demo.domain.ChatRoomJoin;
import com.example.demo.domain.Users;
import com.example.demo.repository.ChatRoomJoinRepository;
import com.example.demo.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatRoomJoinService {
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UsersService usersService;
    public List<ChatRoomJoin> findByUser(Users user) {
        return chatRoomJoinRepository.findByUser(user);
    }

    public Long check(String user1,String user2){
        Users userFirst = usersService.findByName(user1);
        List<ChatRoomJoin> listFirst = chatRoomJoinRepository.findByUser(userFirst);
        Set<ChatRoom> setFirst = new HashSet<>();
        for(ChatRoomJoin chatRoomJoin : listFirst){
            setFirst.add(chatRoomJoin.getChatRoom());
        }
        Users userSecond = usersService.findByName(user2);
        List<ChatRoomJoin> listSecond = chatRoomJoinRepository.findByUser(userSecond);
        for(ChatRoomJoin chatRoomJoin : listSecond){
            if(setFirst.contains(chatRoomJoin.getChatRoom())){
                return chatRoomJoin.getChatRoom().getId();
            }
        }
        return 0L;
    }
    public Long newRoom(String user1, String user2) {
        Long ret = check(user1,user2);
        if(ret != 0){
            return ret;
        }
        ChatRoom chatRoom = new ChatRoom();
        ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);
        ChatRoomJoin chatRoomJoin1 = new ChatRoomJoin();
        chatRoomJoin1.setChatRoom(newChatRoom);
        chatRoomJoin1.setUser(usersService.findByName(user1));
        ChatRoomJoin chatRoomJoin2 = new ChatRoomJoin();
        chatRoomJoin2.setChatRoom(newChatRoom);
        chatRoomJoin2.setUser(usersService.findByName(user2));
        chatRoomJoinRepository.save(chatRoomJoin1);
        chatRoomJoinRepository.save(chatRoomJoin2);
        return newChatRoom.getId();
    }

    public List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom) {
        return chatRoomJoinRepository.findByChatRoom(chatRoom);
    }
}
