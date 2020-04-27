package com.example.demo.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ChatRoomForm {
    private Long id;
    private String writer;
    private String lastMessage;
    private LocalDateTime time;
}
