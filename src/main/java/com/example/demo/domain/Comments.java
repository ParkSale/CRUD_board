package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comments {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;
    @Column(nullable = false)
    private LocalDateTime registerTime;
    public Comments(Posts post, Users user, String comment, LocalDateTime registerTime){
        this.post=post;
        this.user=user;
        this.comment=comment;
        this.registerTime=registerTime;
    }
}
