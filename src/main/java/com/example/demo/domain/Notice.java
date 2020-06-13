package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
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

    public Notice(Type type, Users user, String content, String link, LocalDateTime time){
        this.type = type;
        this.user=user;
        this.content = content;
        this.link=link;
        this.time = time;
    }
}
