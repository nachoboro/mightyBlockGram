package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import com.mightyblockgram.mightyblockgram.service.AccountService;
import com.mightyblockgram.mightyblockgram.service.LikeService;
import com.mightyblockgram.mightyblockgram.service.PostService;
import com.mightyblockgram.mightyblockgram.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class LikeControllerTest {

    @Mock
    private LikeService likeService;

    @Mock
    private AccountService accountService;

    @Mock
    private PostService postService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private LikeController likeController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void whenLikingWithInvalidAccountThenBadRequestShouldBeReturned(){
        doReturn("test").when(jwtUtil).extractUsername(anyString());
        doReturn(null).when(accountService).getAccountByName(eq("test"));
        ResponseEntity result = likeController.likePost(2,"token");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenLikingInvalidPostThenBadRequestShouldBeReturned(){
        doReturn("test").when(jwtUtil).extractUsername(anyString());
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccountByName(eq("test"));
        doReturn(null).when(postService).getPost(anyInt());
        ResponseEntity result = likeController.likePost(2,"token");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenCreatingLikeThenCreatedStatusShouldBeReturned(){
        doReturn("test").when(jwtUtil).extractUsername(anyString());
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccountByName(eq("test"));
        doReturn(new PostDto()).when(postService).getPost(anyInt());
        doReturn(null).when(likeService).getLike(anyString(), anyInt());
        ResponseEntity result = likeController.likePost(2,"token");
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void whenLikingUnlikedPostThenOkShouldBeReturned(){
        doReturn("test").when(jwtUtil).extractUsername(anyString());
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccountByName(eq("test"));
        doReturn(new PostDto()).when(postService).getPost(anyInt());
        doReturn(new LikeDto(2,4,false)).when(likeService).getLike(anyString(), anyInt());
        ResponseEntity result = likeController.likePost(2,"token");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void whenLikingLikedPostThenBadRequestShouldBeReturned(){
        doReturn("test").when(jwtUtil).extractUsername(anyString());
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccountByName(eq("test"));
        doReturn(new PostDto()).when(postService).getPost(anyInt());
        doReturn(new LikeDto(2,4,true)).when(likeService).getLike(anyString(), anyInt());
        ResponseEntity result = likeController.likePost(2,"token");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenUnlikingUnlikedPostThenBadRequestShouldBeReturned(){
        doReturn("test").when(jwtUtil).extractUsername(anyString());
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccountByName(eq("test"));
        doReturn(new PostDto()).when(postService).getPost(anyInt());
        doReturn(new LikeDto(2,4,false)).when(likeService).getLike(anyString(), anyInt());
        ResponseEntity result = likeController.unlikePost(2,"token");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenUnlikingLikedPostThenOkShouldBeReturned(){
        doReturn("test").when(jwtUtil).extractUsername(anyString());
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccountByName(eq("test"));
        doReturn(new PostDto()).when(postService).getPost(anyInt());
        doReturn(new LikeDto(2,4,true)).when(likeService).getLike(anyString(), anyInt());
        ResponseEntity result = likeController.unlikePost(2,"token");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void whenUnlikingWithInvalidAccountThenBadRequestShouldBeReturned(){
        doReturn("test").when(jwtUtil).extractUsername(anyString());
        doReturn(null).when(accountService).getAccountByName(eq("test"));
        ResponseEntity result = likeController.unlikePost(2,"token");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenUnlikingInvalidPostThenBadRequestShouldBeReturned(){
        doReturn("test").when(jwtUtil).extractUsername(anyString());
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccountByName(eq("test"));
        doReturn(null).when(postService).getPost(anyInt());
        ResponseEntity result = likeController.unlikePost(2,"token");
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
