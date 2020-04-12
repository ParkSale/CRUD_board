package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comments {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post;
    @Column(nullable = false)
    private String author;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;
    private LocalDateTime registerTime;
}
