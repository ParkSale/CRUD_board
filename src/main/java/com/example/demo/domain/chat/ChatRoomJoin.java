package com.example.demo.domain.chat;

import com.example.demo.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ChatRoomJoin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name =  "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom chatRoom;

    public ChatRoomJoin(Users user , ChatRoom chatRoom){
        this.user=user;
        this.chatRoom=chatRoom;
    }
}
