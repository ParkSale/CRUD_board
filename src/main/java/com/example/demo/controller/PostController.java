package com.example.demo.controller;

import com.example.demo.domain.Pagination;
import com.example.demo.domain.Posts;
import com.example.demo.domain.UserInfo;
import com.example.demo.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {
    private final UserInfo userInfo;
    private final PostsService postsService;

    @GetMapping("/board/lists/{page}")
    public String showBoard(Model model, @PathVariable("page") int page){
        List<Posts> boardAll = postsService.findAll();
        int totalCnt = boardAll.size();
        List<Posts> board = new ArrayList<>();
        Pagination pagination = new Pagination();
        pagination.pageInfo(page, totalCnt);
        int size = pagination.getListSize();
        for(int i = (page - 1)*size; i < Math.min(totalCnt,(page - 1)*size + size);++i){
            board.add(boardAll.get(i));
        }
        model.addAttribute("pagination",pagination);
        model.addAttribute("posts",board);
        return "board/lists";
    }

    @GetMapping("/board/newPost")
    public String newPost(Model model){
        if(userInfo.getUserName() == ""){
            return "redirect:/";
        }
        PostForm postForm = new PostForm();
        postForm.setAuthor(userInfo.getUserName());
        model.addAttribute("postForm", postForm);
        return "board/newPost";
    }
    @PostMapping("/posts/new")
    public String registerPost(@RequestParam("img") MultipartFile multipartFile, PostForm postForm) throws IOException {
        if(userInfo.getUserName() == ""){
            return "redirect:/";
        }
        Posts post = new Posts();
        post.setTitle(postForm.getTitle());
        post.setAuthor(userInfo.getUserName());
        post.setContent(postForm.getContent());
        if(!multipartFile.isEmpty()){
            postsService.fileUpload(post, multipartFile);
        }
        else{
            post.setRealFileName("");
            post.setFileName("");
        }
        postsService.save(post);
        return "redirect:/board/lists/1";
    }

    @GetMapping("/posts/{postId}/read")
    public String readPost(@PathVariable("postId") Long postId, Model model){
        if(userInfo.getUserName() == ""){
            return "redirect:/";
        }
        Posts post = postsService.findOne(postId);
        model.addAttribute("post",post);
        model.addAttribute("userName",userInfo.getUserName());
        return "board/read";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId, Model model){
        if(userInfo.getUserName() == ""){
            return "redirect:/";
        }
        Posts post = postsService.findOne(postId);
        PostForm postForm = new PostForm();
        postForm.setId(post.getId());
        postForm.setAuthor(post.getAuthor());
        postForm.setContent(post.getContent());
        postForm.setTitle(post.getTitle());
        postForm.setFileName(post.getFileName());
        postForm.setRealFileName(post.getRealFileName());
        model.addAttribute("post",postForm);
        return "board/edit";
    }

    @PostMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId, PostForm postForm){
        if(userInfo.getUserName() == ""){
            return "redirect:/";
        }
        Posts post = postsService.findOne(postId);
        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());
        postsService.save(post);
        return "redirect:/board/lists/1";
    }

    @GetMapping("/posts/{postId}/del")
    public String delPost(@PathVariable("postId") Long postId){
        if(userInfo.getUserName() == ""){
            return "redirect:/";
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
            List<Posts> boardAll = postsService.findByAuthor(str);
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
