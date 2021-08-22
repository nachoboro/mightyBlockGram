package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import com.mightyblockgram.mightyblockgram.service.AccountService;
import com.mightyblockgram.mightyblockgram.service.LikeService;
import com.mightyblockgram.mightyblockgram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    AccountService accountService;

    @Autowired
    PostService postService;

    @PutMapping("/likes")
    public ResponseEntity createOrUpdateLike(@RequestParam int accountId, @RequestParam int postId){
        AccountDto accountDto = accountService.getAccount(accountId);
        if (accountDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exist");
        }
        PostDto postDto = postService.getPost(postId);
        if (postDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("post does not exist");
        }
        LikeDto like = likeService.getLike(accountId, postId);
        if (like == null){
            return ResponseEntity.status(HttpStatus.CREATED).body(likeService.createLike(accountId, postId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(likeService.updateLike(accountId, postId));
    }
}
