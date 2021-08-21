package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/post/list")
    public String getAllPosts() {
        return postRepository.getAllPosts();
    }
}
