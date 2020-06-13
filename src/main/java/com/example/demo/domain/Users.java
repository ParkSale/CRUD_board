package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
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
    private List<Follow> followings;
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> followers;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notice> notices;

    public Users(String email, String password,String name){
        this.email = email;
        this.password=password;
        this.name=name;
    }
}
