package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import com.mightyblockgram.mightyblockgram.service.AccountService;
import com.mightyblockgram.mightyblockgram.service.PostService;
import com.mightyblockgram.mightyblockgram.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/posts")
    public ResponseEntity getAllPosts(@RequestParam int offset, @RequestParam int limit) {
        List<PostDto> postDtoList =  postService.getPosts(offset,limit);
        return ResponseEntity.status(HttpStatus.OK).body(postDtoList);
    }

    @PostMapping(value = "/posts",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity createPost(@RequestParam MultipartFile image, @RequestParam String description, @RequestHeader("Auth-Token") String token){
        String username = jwtUtil.extractUsername(token);
        AccountDto accountDto = accountService.getAccountByName(username);
        if (accountDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exist");
        }
        PostDto createdPost = postService.savePost(image, description, accountDto.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);

    }
}
