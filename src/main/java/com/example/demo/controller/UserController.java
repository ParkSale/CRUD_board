package com.example.demo.controller;

import com.example.demo.domain.Comments;
import com.example.demo.domain.Follow;
import com.example.demo.domain.Posts;
import com.example.demo.domain.Users;
import com.example.demo.domain.chat.ChatRoomJoin;
import com.example.demo.service.FollowService;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final FollowService followService;
    @GetMapping("/users/new")
    public String makeUserForm(Model model){
        model.addAttribute("userForm",new UserForm());
        model.addAttribute("email","");
        model.addAttribute("name","");
        return "user/new";
    }

    @PostMapping("/users/new")
    public String registerUser(UserForm userForm, Model model){
        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
        String ret = usersService.join(userForm);
        model.addAttribute("userForm",userForm);
        model.addAttribute("email","");
        model.addAttribute("name","");
        if(ret.equals("email")){
            model.addAttribute("email","fail");
        }
        if(ret.equals("name")){
            model.addAttribute("name","fail");
        }
        if(ret.equals("success")) return "redirect:/home";
        else return "user/new";
    }

    @GetMapping("/users/myPage/{id}")
    public String myPage(@PathVariable Long id, Model model, HttpServletRequest request){
        Optional<Users> pageUser = usersService.findById(id);
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Users user = usersService.findByEmail(email);
        List<Posts> posts = pageUser.get().getPosts();
        Collections.reverse(posts);
        int chk = followService.check(pageUser.get(),user);
        model.addAttribute("follow",chk);
        model.addAttribute("user",pageUser.get());
        model.addAttribute("posts",posts);
        model.addAttribute("comments",pageUser.get().getComments().size());
        model.addAttribute("follower",pageUser.get().getFollowers().size());
        model.addAttribute("following",pageUser.get().getFollowings().size());
        model.addAttribute("userId",user.getId());
        return "user/myPage";
    }

    @RequestMapping("/autocomplete")
    @ResponseBody
    public List<String> autoComplete(@RequestParam("term") String receiver){
        List<String> ret = usersService.findNameByContaining("%" + receiver+ "%");
        return ret;
    }

    @PostMapping("/users/follow/{id1}/{id2}")
    @ResponseBody
    public void follow(@PathVariable("id1") Long id1, @PathVariable("id2")Long id2){
        Users user1 = usersService.findById(id1).get();
        Users user2 = usersService.findById(id2).get();
        followService.follow(user1,user2);
    }

    @PostMapping("/users/unfollow/{id1}/{id2}")
    @ResponseBody
    public void unfollow(@PathVariable("id1") Long id1, @PathVariable("id2")Long id2){
        Users user1 = usersService.findById(id1).get();
        Users user2 = usersService.findById(id2).get();
        followService.unfollow(user1,user2);

    }
}