package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String writer;
    private String message;
    private MessageType type;
}