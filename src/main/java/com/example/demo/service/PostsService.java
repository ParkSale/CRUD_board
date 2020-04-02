package com.example.demo.service;

import com.example.demo.domain.Posts;
import com.example.demo.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional(readOnly = true)
    public List<Posts> findAll() {
        return postsRepository.findAll();
    }

    @Transactional
    public void save(Posts post) {
        postsRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Posts findOne(Long postId) {
        return postsRepository.findOne(postId);
    }

    @Transactional
    public void delete(Long postId) {
        postsRepository.delete(postId);
    }

    public void fileUpload(Posts post, MultipartFile multipartFile) throws IOException {
        String imgUploadPath = "C:\\Users\\세일\\IdeaProjects\\demo\\src\\main\\resources\\static\\images\\";
        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String realFileName = System.currentTimeMillis() + fileExtension;
        File target = new File(imgUploadPath,realFileName);
        multipartFile.transferTo(target);
        post.setFileName(fileName);
        post.setRealFileName(realFileName);
    }
}

