package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Type type;
    private String link;
    //Message인 경우 채팅방번호, 댓글인 경우 게시글 번호, 팔로우인경우 마이페이지 번호
    @ManyToOne
    private Users user;
    private LocalDateTime time;
}
