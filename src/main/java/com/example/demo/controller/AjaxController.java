package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.FollowService;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class AjaxController {
    private final UsersService usersService;
    private final FollowService followService;
    @GetMapping("/users/nameChk/{name}")
    public String nameChk(@PathVariable("name") String name){
        if(usersService.findByName(name)!= null){
            return "success";
        }
        return "false";
    }

    @GetMapping("/users/getPosts")
    public Map<String, Object> getPost(String name) {
        Users user = usersService.findByName(name);
        List<Long> idList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        List<Long> viewCntList = new ArrayList<>();
        List<LocalDateTime> postTimeList = new ArrayList<>();
        for(Posts post : user.getPosts()){
            idList.add(post.getId());
            titleList.add(post.getTitle());
            viewCntList.add(post.getViewCnt());
            postTimeList.add(post.getPostTime());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id",idList);
        map.put("title",titleList);
        map.put("viewCnt",viewCntList);
        map.put("postTime",postTimeList);
        return map;
    }

    @GetMapping("/users/getComments")
    public Map<String,Object> getComments(String name){
        Map<String,Object> map = new HashMap<>();
        Users user = usersService.findByName(name);
        List<Long> idList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        List<String> contentList = new ArrayList<>();
        List<LocalDateTime> timeList = new ArrayList<>();
        for(Comments comment : user.getComments()){
            idList.add(comment.getPost().getId());
            titleList.add(comment.getPost().getTitle());
            contentList.add(comment.getComment());
            timeList.add(comment.getRegisterTime());
        }
        map.put("id",idList);
        map.put("title",titleList);
        map.put("comment",contentList);
        map.put("time",timeList);
        return map;
    }

    @GetMapping("/users/getFollowings")
    public Map<String,Object> getFollowings(String name){
        Map<String,Object> map = new HashMap<>();
        Users user = usersService.findByName(name);
        List<Long> idList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for(Follow follow : user.getFollowings()){
            idList.add(follow.getFollower().getId());
            nameList.add(follow.getFollower().getName());
        }
        map.put("id",idList);
        map.put("name",nameList);
        return map;
    }

    @GetMapping("/users/getFollowers")
    public Map<String,Object> getFollowers(String name){
        Map<String,Object> map = new HashMap<>();
        Users user = usersService.findByName(name);
        List<Long> idList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<Integer> chkList = new ArrayList<>();
        for(Follow follow : user.getFollowers()){
            idList.add(follow.getFollowing().getId());
            nameList.add(follow.getFollowing().getName());
            chkList.add(followService.check(follow.getFollowing(),user));
        }
        map.put("id",idList);
        map.put("name",nameList);
        map.put("chk",chkList);
        return map;
    }

    @GetMapping("/users/notice")
    public Map<String,Object> getNotice(String name){
        Map<String,Object> map = new HashMap<>();
        Users user = usersService.findByName(name);
        List<String> contents = new ArrayList<>();
        List<String> links = new ArrayList<>();
        List<LocalDateTime> times = new ArrayList<>();
        List<Notice> notices = user.getNotices();
        for(Notice notice : notices){
            contents.add(notice.getContent());
            links.add(notice.getLink());
            times.add(notice.getTime());
        }
        map.put("content",contents);
        map.put("link",links);
        map.put("time",times);
        return map;
    }
}
