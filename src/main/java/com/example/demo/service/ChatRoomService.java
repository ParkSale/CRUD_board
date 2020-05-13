package com.example.demo.service;

import com.example.demo.controller.ChatRoomForm;
import com.example.demo.domain.Users;
import com.example.demo.domain.chat.ChatMessage;
import com.example.demo.domain.chat.ChatRoom;
import com.example.demo.domain.chat.ChatRoomJoin;
import com.example.demo.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinService chatRoomJoinService;
    @Transactional(readOnly = true)
    public Optional<ChatRoom> findById(Long id) {
        return chatRoomRepository.findById(id);
    }

    public List<ChatRoomForm> setting(List<ChatRoomJoin> chatRoomJoins, Users user) {
        List<ChatRoomForm> chatRooms = new ArrayList<>();
        for(ChatRoomJoin tmp : chatRoomJoins){
            ChatRoomForm chatRoomForm = new ChatRoomForm();
            ChatRoom chatRoom = tmp.getChatRoom();
            chatRoomForm.setId(chatRoom.getId());
            if(chatRoom.getMessages().size() != 0) {
                ChatMessage lastMessage = chatRoom.getMessages().get(chatRoom.getMessages().size() - 1);
                chatRoomForm.setLastMessage(lastMessage.getMessage());
                chatRoomForm.setWriter(chatRoomJoinService.findAnotherUser(chatRoom, user.getName()));
                chatRoomForm.setTime(lastMessage.getTime());
                chatRooms.add(chatRoomForm);
            }
            else{
                chatRoomJoinService.delete(tmp);
            }
        }
        Collections.sort(chatRooms, new Comparator<ChatRoomForm>() {
            @Override
            public int compare(ChatRoomForm c1, ChatRoomForm c2) {
                if(c1.getTime().isAfter(c2.getTime())){
                    return -1;
                }
                else{
                    return 1;
                }
            }
        });
        return chatRooms;
    }
}
