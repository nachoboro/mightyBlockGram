package com.mightyblockgram.mightyblockgram.controller;

import com.mightyblockgram.mightyblockgram.dto.AccountDto;
import com.mightyblockgram.mightyblockgram.dto.LikeDto;
import com.mightyblockgram.mightyblockgram.dto.PostDto;
import com.mightyblockgram.mightyblockgram.service.AccountService;
import com.mightyblockgram.mightyblockgram.service.LikeService;
import com.mightyblockgram.mightyblockgram.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class LikeControllerTest {

    @Mock
    private LikeService likeService;

    @Mock
    private AccountService accountService;

    @Mock
    private PostService postService;

    @InjectMocks
    private LikeController likeController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void whenCreatingOrUpdatingLikeWithInvalidAccountThenBadRequestShouldBeReturned(){
        doReturn(null).when(accountService).getAccount(anyInt());
        ResponseEntity result = likeController.createOrUpdateLike(2,4);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenCreatingOrUpdatingLikeWithInvalidPostThenBadRequestShouldBeReturned(){
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccount(anyInt());
        doReturn(null).when(postService).getPost(anyInt());
        ResponseEntity result = likeController.createOrUpdateLike(2,4);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void whenCreatingLikeThenCreatedStatusShouldBeReturned(){
        doReturn(new PostDto()).when(postService).getPost(anyInt());
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccount(anyInt());
        doReturn(null).when(likeService).getLike(anyInt(), anyInt());
        ResponseEntity result = likeController.createOrUpdateLike(2,4);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void whenUpdatingLikeThenOkStatusShouldBeReturned(){
        doReturn(new PostDto()).when(postService).getPost(anyInt());
        doReturn(new AccountDto("user", "pass")).when(accountService).getAccount(anyInt());
        doReturn(new LikeDto(2,4,true)).when(likeService).getLike(anyInt(), anyInt());
        ResponseEntity result = likeController.createOrUpdateLike(2,4);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
