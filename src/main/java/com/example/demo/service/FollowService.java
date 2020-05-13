package com.example.demo.service;

import com.example.demo.domain.Follow;
import com.example.demo.domain.Users;
import com.example.demo.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    public int check(Users user1, Users user2) {
        //user2가 user1을 팔로우 했는가?
        if(user1.equals(user2)){
            return 0;
        }
        for(Follow follow : user2.getFollowings()){
            if(follow.getFollower().equals(user1)){
                return 1;
            }
        }
        return -1;
    }
    public void follow(Users user1, Users user2) {
        //user1이 user2를 팔로우
        Follow follow = new Follow();
        follow.setFollower(user2);
        follow.setFollowing(user1);
        followRepository.save(follow);
    }
    public void unfollow(Users user1, Users user2) {
        //user1이 user2를 언팔로우
        Set<Follow> follows = user1.getFollowings();
        for(Follow follow : follows){
            if(follow.getFollower().equals(user2)){
                follows.remove(follow);
                followRepository.delete(follow);
                return;
            }
        }
    }
}
