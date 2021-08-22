package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import com.mightyblockgram.mightyblockgram.service.AccountService;
import com.mightyblockgram.mightyblockgram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    AccountService accountService;

    @GetMapping("/post/list")
    public ResponseEntity getAllPosts(@RequestParam int offset, @RequestParam int limit) {
        List<PostDto> postDtoList =  postService.getPosts(offset,limit);
        return ResponseEntity.status(HttpStatus.OK).body(postDtoList);
    }

    @PostMapping("/post")
    public ResponseEntity createPost(@RequestParam MultipartFile image, @RequestParam Integer accountId, @RequestParam String description){
        if (image.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image cannot be null or empty");
        }
        AccountDto accountDto = accountService.getAccount(accountId);
        if (accountDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exist");
        }
        try {
            PostDto createdPost = postService.savePost(image, accountId, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred creating post");
        }

    }
}
