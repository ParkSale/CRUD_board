package com.example.demo.controller;

import com.example.demo.domain.Comments;
import com.example.demo.domain.Pagination;
import com.example.demo.domain.Posts;
import com.example.demo.domain.Users;
import com.example.demo.service.CommentsService;
import com.example.demo.service.PostsService;
import com.example.demo.service.S3Service;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostsService postsService;
    private final S3Service s3Service;
    private final UsersService usersService;
    @GetMapping("/board/lists/{page}")
    public String showBoard(Model model, @PathVariable("page") int page){
        List<Posts> boardAll = postsService.findAll();
        int totalCnt = boardAll.size();
        List<Posts> board = new ArrayList<>();
        Pagination pagination = new Pagination();
        pagination.pageInfo(page, totalCnt);
        int size = pagination.getListSize();
        for(int i = (page - 1)*size; i < Math.min(totalCnt,(page - 1)*size + size);++i){
            if(boardAll.get(i).getTitle().length() > 10){
                Posts tmp = boardAll.get(i);
                tmp.setTitle(tmp.getTitle().substring(0,9) + "...");
                board.add(tmp);
            }
            else board.add(boardAll.get(i));
        }
        model.addAttribute("pagination",pagination);
        model.addAttribute("posts",board);
        return "board/lists";
    }

    @GetMapping("/board/newPost")
    public String newPost(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return "redirect:/home";
        }
        PostForm postForm = new PostForm();
        postForm.setUser(user);
        model.addAttribute("postForm", postForm);
        return "board/newPost";
    }
    @PostMapping("/posts/new")
    public String registerPost(@RequestParam("img") MultipartFile multipartFile, PostForm postForm, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return "redirect:/home";
        }
        Posts post = new Posts();
        post.setTitle(postForm.getTitle());
        post.setUser(user);
        post.setContent(postForm.getContent());
        post.setViewCnt((long) 0);
        post.setPostTime(LocalDateTime.now());
        if(!multipartFile.isEmpty()){
            String fileName = s3Service.upload(multipartFile);
            post.setFileName(fileName);
        }
        else{
            post.setFileName("");
        }
        postsService.save(post);
        return "redirect:/board/lists/1";
    }

    @GetMapping("/posts/{postId}/read")
    public String readPost(@PathVariable("postId") Long postId, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        Posts post = postsService.findOne(postId);
        postsService.readPost(post);
        model.addAttribute("post",post);
        model.addAttribute("commentForm",new CommentForm());
        List<Comments> list = post.getComments();
        model.addAttribute("comments",list);
        if(user == null){
            model.addAttribute("userName","");
        }
        else{
            model.addAttribute("userName",user.getName());
        }
        return "board/read";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return "redirect:/home";
        }
        Posts post = postsService.findOne(postId);
        PostForm postForm = new PostForm();
        postForm.setId(post.getId());
        postForm.setUser(user);
        postForm.setContent(post.getContent());
        postForm.setTitle(post.getTitle());
        postForm.setFileName(post.getFileName());
        model.addAttribute("post",postForm);
        return "board/edit";
    }

    @PostMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId, PostForm postForm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return "redirect:/home";
        }
        Posts post = postsService.findOne(postId);
        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());
        postsService.save(post);
        return "redirect:/board/lists/1";
    }

    @GetMapping("/posts/{postId}/del")
    public String delPost(@PathVariable("postId") Long postId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        if(user == null){
            return "redirect:/home";
        }
        postsService.delete(postId);
        return "redirect:/board/lists/1";
    }

    @GetMapping("posts/search/{page}")
    public String search(@RequestParam("type") String type, @RequestParam("str") String str,
                         @PathVariable("page") int page, Model model){
        if(type.equals("title")){
            List<Posts> boardAll = postsService.findByTitle(str);
            int totalCnt = boardAll.size();
            Pagination pagination = postsService.setPagination(page,totalCnt);
            int size = pagination.getListSize();
            List<Posts> board = postsService.getBoard(boardAll,size,page,totalCnt);
            model.addAttribute("pagination",pagination);
            model.addAttribute("posts",board);
            return "board/search";
        }
        else if(type.equals("content")){
            List<Posts> boardAll = postsService.findByContent(str);
            int totalCnt = boardAll.size();
            Pagination pagination = postsService.setPagination(page,totalCnt);
            int size = pagination.getListSize();
            List<Posts> board = postsService.getBoard(boardAll,size,page,totalCnt);
            model.addAttribute("pagination",pagination);
            model.addAttribute("posts",board);
            return "board/search";
        }
        else{
            Users user = usersService.findByName(str);
            List<Posts> boardAll = user.getPosts();
            Collections.reverse(boardAll);
            int totalCnt = boardAll.size();
            Pagination pagination = postsService.setPagination(page,totalCnt);
            int size = pagination.getListSize();
            List<Posts> board = postsService.getBoard(boardAll,size,page,totalCnt);
            model.addAttribute("pagination",pagination);
            model.addAttribute("posts",board);
            return "board/search";
        }
    }
}
