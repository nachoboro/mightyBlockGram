package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import com.mightyblockgram.mightyblockgram.service.AccountService;
import com.mightyblockgram.mightyblockgram.service.LikeService;
import com.mightyblockgram.mightyblockgram.service.PostService;
import com.mightyblockgram.mightyblockgram.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;

    @PutMapping("/posts/{postId}/like")
    public ResponseEntity likePost(@PathVariable int postId, @RequestHeader("Auth-Token") String token){
        String username = jwtUtil.extractUsername(token);
        AccountDto accountDto = accountService.getAccountByName(username);
        if (accountDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exist");
        }
        PostDto postDto = postService.getPost(postId);
        if (postDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post does not exist");
        }
        LikeDto like = likeService.getLike(username, postId);
        if (like == null){
            return ResponseEntity.status(HttpStatus.CREATED).body(likeService.createLike(username, postId));
        }
        if (like.isActive()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot like an already liked post");
        }
        return ResponseEntity.status(HttpStatus.OK).body(likeService.likePost(username, postId));
    }

    @PutMapping("/posts/{postId}/unlike")
    public ResponseEntity unlikePost(@PathVariable int postId, @RequestHeader("Auth-Token") String token){
        String username = jwtUtil.extractUsername(token);
        AccountDto accountDto = accountService.getAccountByName(username);
        if (accountDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exist");
        }
        PostDto postDto = postService.getPost(postId);
        if (postDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("post does not exist");
        }
        LikeDto like = likeService.getLike(username, postId);
        if (!like.isActive()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot unlike an already unliked post");
        }
        return ResponseEntity.status(HttpStatus.OK).body(likeService.unlikePost(username, postId));
    }
}
