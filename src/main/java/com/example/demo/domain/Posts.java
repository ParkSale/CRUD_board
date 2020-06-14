package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity @Setter
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private String fileName;
    private Long viewCnt;
    private LocalDateTime postTime;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();

    public void plusViewCnt() {
        this.viewCnt++;
    }

    public Posts(String title, String content,Users user, LocalDateTime postTime){
        this.title=title;
        this.content=content;
        this.user=user;
        this.postTime = postTime;
        this.viewCnt= Long.valueOf(0);
    }
}
