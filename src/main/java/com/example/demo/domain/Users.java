package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
}
