package com.example.demo.domain;

import com.example.demo.domain.chat.ChatRoomJoin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    private List<Posts> posts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comments> comments;
    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private Set<Follow> followings = new HashSet<>();
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private Set<Follow> followers = new HashSet<>();
}
